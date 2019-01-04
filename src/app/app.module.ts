import { AuthGuard } from './guards/auth.guard';
import { AppRountingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule, LOCALE_ID } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpModule, Http, RequestOptions } from "@angular/http";
import { LocationStrategy, HashLocationStrategy } from "@angular/common";
import { ToastrModule } from "ngx-toastr";
import { AppComponent } from "./app.component";

// App modules/components
import { BasicModule } from "./components/layouts/basic.module";
import { OAuthService } from "./services/oauth.service";
import { AuthConfig, AuthHttp } from "angular2-jwt";
import { TeatrouHttp } from "./guards/teatrou-http";

export function authHttpServiceFactory(
  auth: OAuthService,
  http: Http,
  options: RequestOptions
) {
  const config = new AuthConfig({
    globalHeaders: [{ "Content-Type": "application/json" }]
  });

  return new TeatrouHttp(auth, config, http, options);
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BasicModule,
    AppRountingModule,
    ToastrModule.forRoot({
      progressBar: true,
      progressAnimation: "increasing"
    }) // ToastrModule added
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [OAuthService, Http, RequestOptions]
    },
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
