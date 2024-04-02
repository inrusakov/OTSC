import { Component, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'image-view',
  templateUrl: './image-view.component.html',
  styleUrls: ['./image-view.component.css']
})
export class ImageView {
  @Input() imageId!: string;

  imageBase64!: string;
  imagePath!: any;

  constructor(private axiosService: AxiosService, private _sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.axiosService.sendFile(
      "GET",
      "/photos/" + this.imageId, {})
      .then(
        response => {
          this.imageBase64 = response.data.image.data;
          this.imagePath = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.imageBase64);
        }).catch(
          error => {
            this.imagePath = 'Not Found'
          }
        );
  }
}
