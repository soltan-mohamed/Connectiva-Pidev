// src/app/components/matches/matches.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {
  matches: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

// matches.component.ts
ngOnInit() {
  this.route.params.subscribe(params => {
    const userId = params['userId']; // Match the route parameter name
    this.userService.getMatches(userId).subscribe({
      next: (matches) => {
        console.log('Matches data:', matches);
        this.matches = matches;
      },
      error: (err) => console.error('Error:', err)
    });
  });
}
}