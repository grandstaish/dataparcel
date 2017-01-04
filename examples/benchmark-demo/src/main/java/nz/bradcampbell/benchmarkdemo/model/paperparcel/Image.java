/*
 * Copyright (C) 2016 Bradley Campbell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.bradcampbell.benchmarkdemo.model.paperparcel;

import android.os.Parcel;
import android.os.Parcelable;
import paperparcel.PaperParcel;

@PaperParcel
public final class Image implements Parcelable {
  public static final Creator<Image> CREATOR = PaperParcelImage.CREATOR;

  private final String id;
  private final String format;
  private final String url;
  private final String description;

  public Image(
      String id,
      String format,
      String url,
      String description) {
    this.id = id;
    this.format = format;
    this.url = url;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public String getFormat() {
    return format;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    PaperParcelImage.writeToParcel(this, dest, flags);
  }
}
