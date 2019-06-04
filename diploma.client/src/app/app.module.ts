import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import {ToastrModule} from 'ngx-toastr';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {LoginService} from "./login/login.service";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ClientListComponent} from './client-list/client-list.component';
import {ClientListService} from "./client-list/client-list.service";
import {AboutComponent} from './about/about.component';
import {OrdersComponent} from './orders/orders.component';
import {OrderComponent} from './orders/order/order.component';
import {OrderItemsComponent} from './orders/order-items/order-items.component';
import {AppRoutingModule} from './app-routing.module';
import {OrderService} from "./orders/shared/order.service";
import {HeaderComponent} from './header/header.component';
import {RegistrationComponent} from './registration/registration.component';
import {BookingComponent} from './booking/booking.component';
import {NextOperationComponent} from './next-operation/next-operation.component';
import {
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatInputModule,
  MatNativeDateModule,
  MatProgressSpinnerModule,
  MatSelectModule
} from "@angular/material";
import {AlertComponent} from './alert/alert.component';
import {TableSelectionComponent} from './table-selection/table-selection.component';
import {BoldDirectiveDirective} from './bold-directive.directive';
import {OrderViewComponent} from './orders/order/order-view/order-view.component';
import {CommonModule} from "@angular/common";
import {TextMaskModule} from "angular2-text-mask";
import {ContactUsComponent} from './contact-us/contact-us.component';
import {SimpleInstructionComponent} from './simple-instruction/simple-instruction.component';
import {CommentsComponent} from './comments/comments.component';
import {AmazingTimePickerModule} from "amazing-time-picker";
import {SliderModule} from "angular-image-slider";
import {SlideshowModule} from "ng-simple-slideshow";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    OrdersComponent,
    OrderComponent,
    OrderItemsComponent,
    HeaderComponent,
    RegistrationComponent,
    BookingComponent,
    NextOperationComponent,
    AlertComponent,
    TableSelectionComponent,
    BoldDirectiveDirective,
    OrderViewComponent,
    ContactUsComponent,
    SimpleInstructionComponent,
    CommentsComponent,
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule, AppRoutingModule,
    BrowserAnimationsModule, MatDialogModule, MatProgressSpinnerModule, ToastrModule.forRoot(),
    ReactiveFormsModule, CommonModule, TextMaskModule, MatDatepickerModule, MatNativeDateModule, MatInputModule,
    AmazingTimePickerModule, MatSelectModule, MatCardModule, MatButtonModule, SliderModule, SlideshowModule
  ],
  entryComponents: [OrderItemsComponent, NextOperationComponent, AlertComponent, TableSelectionComponent, CommentsComponent],
  providers: [HttpService, LoginService, ClientListService, OrderService, FormBuilder],
  bootstrap: [AppComponent],
})
export class AppModule {
}
