import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { View0Component } from './component/view0/view0.component';
import { View1Component } from './component/view1/view1.component';
import { View2Component } from './component/view2/view2.component';
import { SecondhandService } from './service/secondhand.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    View0Component,
    View1Component,
    View2Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [SecondhandService],
  bootstrap: [AppComponent]
})
export class AppModule { }
