/*
 * Copyright 2019 Wolfgang Reder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.or.reder.media.util;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
@FunctionalInterface
public interface IOBiFunction<T, U, R>
{

  /**
   * Applies this function to the given arguments.
   *
   * @param t the first function argument
   * @param u the second function argument
   * @return the function result
   */
  R apply(T t,
          U u) throws IOException;

  /**
   * Returns a composed function that first applies this function to its input, and then applies the {@code after} function to the
   * result. If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
   *
   * @param <V> the type of output of the {@code after} function, and of the composed function
   * @param after the function to apply after this function is applied
   * @return a composed function that first applies this function and then applies the {@code after} function
   * @throws NullPointerException if after is null
   */
  default <V> IOBiFunction<T, U, V> andThen(IOFunction<? super R, ? extends V> after)
  {
    Objects.requireNonNull(after);
    return (T t, U u) -> after.apply(apply(t,
                                           u));
  }

}
