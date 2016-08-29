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

package paperparcel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Can be applied to any field in a {@link PaperParcel}-annotated class to tell the compiler
 * to ignore that field when generating the {@code Parcelable.Creator} and {@code writeToParcel}
 * implementations.
 *
 * <p>This cannot be applied to anything that is required to construct the
 * {@link PaperParcel}-annotated class, otherwise PaperParcel will not know how to re-construct
 * your object in the generated {@code Parcelable.Creator} implementation.
 *
 * <p>Note that any data associated with the excluded field will be lost when parcelled.
 */
@Documented
@Retention(CLASS)
@Target(FIELD)
public @interface Exclude {
}