package us.lsi.math;

import java.math.BigInteger;
import org.apache.commons.lang3.mutable.Mutable;


public class MutableBigInteger extends Number implements Comparable<MutableBigInteger>, Mutable<Number> {

      private static final long serialVersionUID = 512176391864L;

      private BigInteger value;

      public MutableBigInteger() {            
          super();
      }

      public MutableBigInteger(BigInteger value) {
          super();
          this.value = value;
      }

      public MutableBigInteger(Long value) {
          super();
          this.value = new BigInteger(value.toString());
      }
      
      public MutableBigInteger(String value) throws NumberFormatException {
          super();
          this.value = new BigInteger(value);
      }

      @Override
      public BigInteger getValue() {
          return value;
      }
      
      public void setValue(BigInteger value) {
          this.value = value;
      }

      
      @Override
      public void setValue(Number value) {
          this.value = BigInteger.valueOf(value.longValue());
      }
      
      public void increment() {
          this.value = this.value.add(BigInteger.ONE);
      }

      public void decrement() {
          this.value = this.value.subtract(BigInteger.ONE);
      }
      
      public void add(BigInteger operand) {
          this.value = this.value.add(operand);     
      }
      
      public void add(Number operand) {
          this.value = value.add(BigInteger.valueOf(operand.longValue()));
      }
      
      public void subtract(BigInteger operand) {
          this.value = this.value.subtract(operand);
      }
      
      public void subtract(Number operand) {
          this.value = value.subtract(BigInteger.valueOf(operand.longValue()));
      }
      
      public void multiply(BigInteger operand) {
          this.value = this.value.multiply(operand);
      }
      
      public void multiply(Number operand) {
          this.value = value.multiply(BigInteger.valueOf(operand.longValue()));
      }
      
      @Override
      public int intValue() {
          return value.intValue();
      }
      
      @Override
      public long longValue() {
          return value.longValue();
      }
      
      @Override
      public float floatValue() {
          return value.floatValue();
      }
      
      @Override
      public double doubleValue() {
              return value.doubleValue();
      }
      
      public Integer toInteger() {
          return value.intValue();
      }
      
      @Override
      public boolean equals(Object obj) {
          if (obj instanceof MutableBigInteger) {
              return value.equals(obj);
          }
          return false;
      }
      
      @Override
      public int hashCode() {
          return value.hashCode();
      }
      
      @Override
      public int compareTo(MutableBigInteger other) {
          BigInteger anotherVal = other.value;
          return value.compareTo(anotherVal);
      }
      
      @Override
      public String toString() {
          return value.toString();
      }
    
}

