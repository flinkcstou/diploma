import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {OrdersComponent} from "./orders/orders.component";
import {OrderComponent} from "./orders/order/order.component";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {BookingComponent} from "./booking/booking.component";
import {AboutComponent} from "./about/about.component";
import {AuthGuard} from "./auth/auth.guard";
import {OrderViewComponent} from "./orders/order/order-view/order-view.component";
import {ContactUsComponent} from "./contact-us/contact-us.component";
import {SimpleInstructionComponent} from "./simple-instruction/simple-instruction.component";
import {NotViewStuffGuard} from "./not-view-stuff.guard";

const routes: Routes = [
  {path: '', redirectTo: 'order', pathMatch: 'full'},
  {
    path: 'order', children: [
      {path: '', component: OrderComponent},
      {path: 'edit/:id', component: OrderComponent},
      {path: '', component: OrderComponent},
    ]
  },
  {
    path: 'order-view', children: [
      {path: '', component: OrderViewComponent},
      {path: 'edit/:id', component: OrderViewComponent},
      {path: '', component: OrderViewComponent},
    ]
  },
  {path: 'orders', component: OrdersComponent},
  {path: 'about', component: AboutComponent},
  {path: 'contact', component: ContactUsComponent},
  {path: 'instruction', component: SimpleInstructionComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'booking', component: BookingComponent, },
  {
    path: 'admin', loadChildren: "./admin/admin.module#AdminModule",
    canActivate: [AuthGuard]
  },

  {path: '**', redirectTo: 'order', pathMatch: 'full'},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}


/*
const routes:Routes = [
  // {path:'',redirectTo:'order',pathMatch:'full'},
  {path:'orders',component:OrdersComponent},

]*/
