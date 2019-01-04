import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { UiSwitchModule } from "ngx-ui-switch";
import {
  PaginatorModule,
  CalendarModule,
  DropdownModule,
  InputTextareaModule,
  EditorModule,
  FileUploadModule,
  DataTableModule,
  TooltipModule
} from "primeng/primeng";

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    PaginatorModule,
    CalendarModule,
    DropdownModule,
    InputTextareaModule,
    EditorModule,
    FileUploadModule,
    UiSwitchModule,
    DataTableModule,
    TooltipModule
  ],
  exports: [
    BrowserModule,
    BrowserAnimationsModule,
    PaginatorModule,
    CalendarModule,
    DropdownModule,
    InputTextareaModule,
    EditorModule,
    FileUploadModule,
    UiSwitchModule,
    DataTableModule,
    TooltipModule
  ]
})
export class PrimeModule {}
