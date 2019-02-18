/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.util;

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
