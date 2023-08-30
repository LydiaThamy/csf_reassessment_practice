import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Posting } from 'src/app/model/Posting';
import { SecondhandService } from 'src/app/service/secondhand.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  constructor(private fb: FormBuilder, private router: Router, private service: SecondhandService) { }

  form!: FormGroup
  @ViewChild('image') image!: ElementRef

  ngOnInit(): void {
    this.createForm()
  }

  createForm(): void {
    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control<string>('', [Validators.email, Validators.required, Validators.maxLength(128)]),
      phone: this.fb.control<string>(''),
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(5), Validators.maxLength(128)]),
      description: this.fb.control<string>('', [Validators.required]),
      image: this.fb.control<string>('', [Validators.required])
    })
  }

  postForm(): void {
    const posting: Posting = {
      postingId: '',
      postingDate: '',
      name: this.form.value['name'],
      email: this.form.value['email'],
      phone: this.form.value['phone'],
      title: this.form.value['title'],
      description: this.form.value['description'],
      image: this.image.nativeElement.files[0]
    }

    // this.service.postForm(posting)
    this.service.savePosting(posting).subscribe(
      data => this.router.navigate(['/post', data.postingId])
    )
  }
}
