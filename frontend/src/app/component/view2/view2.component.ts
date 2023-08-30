import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecondhandService } from 'src/app/service/secondhand.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

  constructor(private aRoute: ActivatedRoute, private service: SecondhandService, private router: Router) {}

  postingId!: string

  ngOnInit(): void {
    
    if (this.service.postingSaved != true) {
      this.router.navigate(['/'])
    }

    this.postingId = this.aRoute.snapshot.params['postingId']
    this.service.postingSaved = false
  }

}
