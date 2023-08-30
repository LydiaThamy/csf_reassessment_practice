import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { File } from 'src/app/model/File';
import { SecondhandService } from 'src/app/service/secondhand.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  constructor(private fb: FormBuilder, private router: Router, private service: SecondhandService) { }

  form!: FormGroup

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
    const file: File = this.form.value
    this.service.postForm(file)
    this.router.navigate(['/post'])
  }
}
