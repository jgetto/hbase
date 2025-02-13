/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hbase.thrift2.generated;


/**
 * Thrift wrapper around
 * org.apache.hadoop.hbase.regionserver.BloomType
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2022-07-05")
public enum TBloomFilterType implements org.apache.thrift.TEnum {
  /**
   * Bloomfilters disabled
   */
  NONE(0),
  /**
   * Bloom enabled with Table row as Key
   */
  ROW(1),
  /**
   * Bloom enabled with Table row &amp; column (family+qualifier) as Key
   */
  ROWCOL(2),
  /**
   * Bloom enabled with Table row prefix as Key, specify the length of the prefix
   */
  ROWPREFIX_FIXED_LENGTH(3);

  private final int value;

  private TBloomFilterType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static TBloomFilterType findByValue(int value) { 
    switch (value) {
      case 0:
        return NONE;
      case 1:
        return ROW;
      case 2:
        return ROWCOL;
      case 3:
        return ROWPREFIX_FIXED_LENGTH;
      default:
        return null;
    }
  }
}
