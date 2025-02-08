from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from collections import defaultdict

app = Flask(__name__)

# Domain categorization dictionary
DOMAIN_KEYWORDS = {
    "technology": ["ai", "machine learning", "deep learning", "data science", "python"],
    "sports": ["sport", "football", "basketball", "tennis", "training"],
    # Add more domains as needed
}

@app.route('/match', methods=['POST'])
def match_users():
    data = request.json
    current_user = data['current_user']
    all_users = data['all_users']
    
    # Step 1: Detect current user's domain
    current_domain = detect_domain(current_user['interests'])
    
    # Step 2: Filter users by domain (strict matching)
    domain_users = [
        user for user in all_users 
        if detect_domain(user['interests']) == current_domain
    ]
    
    # Step 3: If no users in the same domain, return empty list
    if not domain_users:
        return jsonify([])
    
    # Step 4: Prepare documents for TF-IDF
    documents = [' '.join(current_user['interests']).lower()]
    documents += [' '.join(user['interests']).lower() for user in domain_users]
    
    # Step 5: Calculate similarity
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(documents)
    
    # Step 6: Get top 3 matches
    cosine_sim = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:])
    top_indices = cosine_sim.argsort()[0][-3:][::-1]
    
    return jsonify([domain_users[i] for i in top_indices])

def detect_domain(interests):
    """Strict domain detection using exact keyword matches"""
    domain_scores = defaultdict(int)
    
    for interest in interests:
        interest_lower = interest.lower()
        for domain, keywords in DOMAIN_KEYWORDS.items():
            # Check for exact keyword matches (not substrings)
            if interest_lower in keywords:
                domain_scores[domain] += 1
                
    if not domain_scores:
        return "other"
        
    return max(domain_scores, key=domain_scores.get)

if __name__ == '__main__':
    app.run(port=5000, debug=True)