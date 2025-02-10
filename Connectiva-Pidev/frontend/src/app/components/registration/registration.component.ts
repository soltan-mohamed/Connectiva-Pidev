// registration.component.ts
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  user = { name: '', email: '', interests: [] as string[] };
  newInterest = '';
  
  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
    console.log('Form submitted:', this.user);
    
    if (form.valid) {
      this.userService.createUser(this.user).subscribe({
        next: (response) => {
          console.log('Registration successful:', response);
          this.router.navigate(['/matches', response.id]);
        },
        error: (err) => {
          console.error('Registration failed:', err);
          alert('Registration failed. Please try again.');
        }
      });
    } else {
      alert('Please fill all required fields!');
    }
  }

  addInterest() {
    if (this.newInterest.trim()) {
      this.user.interests.push(this.newInterest.trim());
      this.newInterest = '';
    }
  }

  // Add to RegistrationComponent class
removeInterest(interest: string) {
  this.user.interests = this.user.interests.filter(i => i !== interest);
}
}