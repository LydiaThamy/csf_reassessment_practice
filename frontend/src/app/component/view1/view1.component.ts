import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Posting } from 'src/app/model/Posting';
import { SecondhandService } from 'src/app/service/secondhand.service';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit, OnDestroy {

  constructor(private service: SecondhandService, private aRoute: ActivatedRoute, private router: Router) { }

  posting!: Posting
  sub$!: Subscription

  ngOnInit(): void {
    const postingId: string = this.aRoute.snapshot.params['postingId']
    this.sub$ = this.service.getPosting(postingId)
      .subscribe({
        next: data => {
        this.posting = data as Posting
      },
        error: (error: HttpErrorResponse) => {
          alert(JSON.stringify(error["error"]["message"]))
          this.router.navigate(['/'])
        }
      })
  }

  confirmPost(): void {
    this.service.confirmPost(this.posting)
      .subscribe({
        next: () => {
          this.service.postingSaved = true
          this.router.navigate(['/confirm', this.posting.postingId])
        },
        error: (error: HttpErrorResponse) => {
          alert(JSON.stringify(error["error"]["message"]))
          this.router.navigate(['/'])
        }
      })
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }
}
