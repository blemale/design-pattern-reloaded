package builder;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface builder5 {
  public interface Vehicle { /* empty */ }
  public class Car implements Vehicle {
    @Override
    public String toString() {
      return "Car "; 
    }
  }
  public class Moto implements Vehicle {
    @Override
    public String toString() {
      return "Moto "; 
    }
  }
  
  static <K, T> Function<K, T> factoryKit(Consumer<BiConsumer<K, T>> consumer, Function<? super K, ? extends T> ifAbsent) {
    HashMap<K, T> map = new HashMap<>();
    consumer.accept(map::put);
    return key -> map.computeIfAbsent(key, ifAbsent);
  }
  
  public static void main(String[] args) {
    Function<String, Supplier<Vehicle>> factory = factoryKit(builder -> {
      builder.accept("car", Car::new);
      builder.accept("moto", Moto::new);  
    }, name -> { throw new IllegalArgumentException("unknown vehicle " + name); });
    
    Vehicle vehicle1 = factory.apply("car").get();
    System.out.println(vehicle1);
    Vehicle vehicle2 = factory.apply("moto").get();
    System.out.println(vehicle2);
  }
}
