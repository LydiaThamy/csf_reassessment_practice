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

  constructor(private service: SecondhandService, private aRoute: ActivatedRoute, private router: Router) {}
  
  posting!: Posting
  sub$!: Subscription
  
  ngOnInit(): void {
    if (this.service.posting$ !== undefined) {
      this.sub$ = this.service.posting$
        .subscribe(data => {
        this.posting = data as Posting
      })

    } else {
      const postingId: string = this.aRoute.snapshot.params['postingId']
      this.sub$ = this.service.getPosting(postingId)
        .subscribe(data => {
          this.posting = data as Posting
        })
    }
  }

  confirmPost(): void {
    this.service.confirmPost(this.posting)
      .subscribe(() => {
        this.router.navigate(['/confirm', this.posting.postingId])
      })
  }
  
  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }
}
