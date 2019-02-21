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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author Wolfgang Reder
 */
public final class Lookup
{

//  public static final class LookupResult<S> implements Comparable<LookupResult<S>>
//  {
//
//    private final Class<? extends S> clazz;
//    private final Constructor<S> ctor;
//    private final int sort;
//    private S instance;
//
//    public LookupResult(Class<? extends S> clazz,
//                        Constructor<S> ctor,
//                        int sort)
//    {
//      this.clazz = Objects.requireNonNull(clazz,
//                                          "clazz is null");
//      this.ctor = Objects.requireNonNull(ctor,
//                                         "ctor is null");
//      this.sort = sort;
//    }
//
//    public synchronized S get()
//    {
//      if (instance == null) {
//        try {
//          instance = ctor.newInstance();
//        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//          MediaUtils.LOGGER.log(Level.SEVERE,
//                                null,
//                                ex);
//        }
//      }
//      return instance;
//    }
//
//    public Class<? extends S> getType()
//    {
//      return clazz;
//    }
//
//    @Override
//    public int hashCode()
//    {
//      int hash = 7;
//      hash = 97 * hash + Objects.hashCode(this.clazz);
//      return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj)
//    {
//      if (this == obj) {
//        return true;
//      }
//      if (obj == null) {
//        return false;
//      }
//      if (getClass() != obj.getClass()) {
//        return false;
//      }
//      final LookupResult<?> other = (LookupResult<?>) obj;
//      return Objects.equals(this.clazz,
//                            other.clazz);
//    }
//
//    @Override
//    public int compareTo(LookupResult<S> o)
//    {
//      return sort - o.sort;
//    }
//
//  }
//
//  public static <S> S lookup(Class<? extends S> clazz)
//  {
//    List<LookupResult<S>> lr = lookupAll(clazz);
//    if (!lr.isEmpty()) {
//      return lr.get(0).get();
//    }
//    return null;
//  }
//
//  @SuppressWarnings({"unchecked"})
//  private static <S> LookupResult<S> loadLookupItem(String clazzName,
//                                                    String sort,
//                                                    Class<? extends S> clazz,
//                                                    ClassLoader loader)
//  {
//    try {
//      Class<?> loaded = loader.loadClass(clazzName);
//      if (!clazz.isAssignableFrom(loaded)) {
//        // TODO log classmismatch
//        return null;
//      }
//      if (clazz.isInterface()) {
//        // TODO log clazz is interface
//        return null;
//      }
//      if (clazz.isMemberClass()) {
//        // TODO log clazz is nonstatic memberclass
//        return null;
//      }
//      Constructor<?> ctor = loaded.getDeclaredConstructor();
//      if (!ctor.canAccess(null)) {
//        // TODO cannot access ctor
//        return null;
//      }
//      int so = Integer.MIN_VALUE;
//      if (sort != null && !sort.isBlank()) {
//        try {
//          so = Integer.parseInt(sort);
//        } catch (Throwable th) {
//          MediaUtils.LOGGER.log(Level.WARNING,
//                                null,
//                                th);
//        }
//      }
//      return new LookupResult<>(clazz,
//                                (Constructor<S>) ctor,
//                                so);
//    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
//      MediaUtils.LOGGER.log(Level.SEVERE,
//                            null,
//                            ex);
//    }
//    return null;
//  }
//  private static <S> List<LookupResult<S>> testLookupURL(URL u,
//                                                         Class<? extends S> clazz,
//                                                         ClassLoader loader)
//  {
//    List<LookupResult<S>> result = new ArrayList<>();
//    try (InputStream is = u.openStream()) {
//      Properties props = new Properties();
//      props.load(is);
//      for (Map.Entry<Object, Object> e : props.entrySet()) {
//        if (e.getKey() != null) {
//          String cz = e.getKey().toString();
//          String s = e.getValue() != null ? e.getValue().toString() : null;
//          LookupResult<S> rl = loadLookupItem(cz,
//                                              s,
//                                              clazz,
//                                              loader);
//          if (rl != null) {
//            result.add(rl);
//          }
//        }
//      }
//    } catch (IOException ex) {
//      MediaUtils.LOGGER.log(Level.SEVERE,
//                            null,
//                            ex);
//    }
//    return result;
//  }
//  public static <S> List<LookupResult<S>> lookupAll(Class<? extends S> clazz)
//  {
//    ArrayList<LookupResult<S>> result = new ArrayList<>();
//    try {
//      ClassLoader loader = clazz.getClassLoader();
//      Enumeration<URL> en = loader.getResources("/META-INF/services/" + clazz.getName());
//      while (en.hasMoreElements()) {
//        URL u = en.nextElement();
//        result.addAll(testLookupURL(u,
//                                    clazz,
//                                    loader));
//        result.sort(null);
//      }
//    } catch (IOException ex) {
//      MediaUtils.LOGGER.log(Level.SEVERE,
//                            null,
//                            ex);
//    }
//    return result;
//  }
  public static <S> List<S> lookupAllInstances(Class<S> clazz)
  {
    ServiceLoader<S> loader = ServiceLoader.load(clazz);
    loader.reload();
    Iterator<S> iter = loader.iterator();
    List<S> result = new ArrayList<>();
    while (iter.hasNext()) {
      result.add(iter.next());
    }
    return result;
//    return lookupAll(clazz).stream().
//            map(LookupResult::get).
//            collect(Collectors.toList());
  }

}
