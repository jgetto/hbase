/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hbase.thrift.generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * An Append object is used to specify the parameters for performing the append operation.
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2022-07-05")
public class TAppend implements org.apache.thrift.TBase<TAppend, TAppend._Fields>, java.io.Serializable, Cloneable, Comparable<TAppend> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAppend");

  private static final org.apache.thrift.protocol.TField TABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("table", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ROW_FIELD_DESC = new org.apache.thrift.protocol.TField("row", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField COLUMNS_FIELD_DESC = new org.apache.thrift.protocol.TField("columns", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("values", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TAppendStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TAppendTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer table; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer row; // required
  public @org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> columns; // required
  public @org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> values; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TABLE((short)1, "table"),
    ROW((short)2, "row"),
    COLUMNS((short)3, "columns"),
    VALUES((short)4, "values");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TABLE
          return TABLE;
        case 2: // ROW
          return ROW;
        case 3: // COLUMNS
          return COLUMNS;
        case 4: // VALUES
          return VALUES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TABLE, new org.apache.thrift.meta_data.FieldMetaData("table", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Text")));
    tmpMap.put(_Fields.ROW, new org.apache.thrift.meta_data.FieldMetaData("row", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Text")));
    tmpMap.put(_Fields.COLUMNS, new org.apache.thrift.meta_data.FieldMetaData("columns", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , "Text"))));
    tmpMap.put(_Fields.VALUES, new org.apache.thrift.meta_data.FieldMetaData("values", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , "Text"))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAppend.class, metaDataMap);
  }

  public TAppend() {
  }

  public TAppend(
    java.nio.ByteBuffer table,
    java.nio.ByteBuffer row,
    java.util.List<java.nio.ByteBuffer> columns,
    java.util.List<java.nio.ByteBuffer> values)
  {
    this();
    this.table = org.apache.thrift.TBaseHelper.copyBinary(table);
    this.row = org.apache.thrift.TBaseHelper.copyBinary(row);
    this.columns = columns;
    this.values = values;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAppend(TAppend other) {
    if (other.isSetTable()) {
      this.table = org.apache.thrift.TBaseHelper.copyBinary(other.table);
    }
    if (other.isSetRow()) {
      this.row = org.apache.thrift.TBaseHelper.copyBinary(other.row);
    }
    if (other.isSetColumns()) {
      java.util.List<java.nio.ByteBuffer> __this__columns = new java.util.ArrayList<java.nio.ByteBuffer>(other.columns.size());
      for (java.nio.ByteBuffer other_element : other.columns) {
        __this__columns.add(org.apache.thrift.TBaseHelper.copyBinary(other_element));
      }
      this.columns = __this__columns;
    }
    if (other.isSetValues()) {
      java.util.List<java.nio.ByteBuffer> __this__values = new java.util.ArrayList<java.nio.ByteBuffer>(other.values.size());
      for (java.nio.ByteBuffer other_element : other.values) {
        __this__values.add(org.apache.thrift.TBaseHelper.copyBinary(other_element));
      }
      this.values = __this__values;
    }
  }

  public TAppend deepCopy() {
    return new TAppend(this);
  }

  @Override
  public void clear() {
    this.table = null;
    this.row = null;
    this.columns = null;
    this.values = null;
  }

  public byte[] getTable() {
    setTable(org.apache.thrift.TBaseHelper.rightSize(table));
    return table == null ? null : table.array();
  }

  public java.nio.ByteBuffer bufferForTable() {
    return org.apache.thrift.TBaseHelper.copyBinary(table);
  }

  public TAppend setTable(byte[] table) {
    this.table = table == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(table.clone());
    return this;
  }

  public TAppend setTable(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer table) {
    this.table = org.apache.thrift.TBaseHelper.copyBinary(table);
    return this;
  }

  public void unsetTable() {
    this.table = null;
  }

  /** Returns true if field table is set (has been assigned a value) and false otherwise */
  public boolean isSetTable() {
    return this.table != null;
  }

  public void setTableIsSet(boolean value) {
    if (!value) {
      this.table = null;
    }
  }

  public byte[] getRow() {
    setRow(org.apache.thrift.TBaseHelper.rightSize(row));
    return row == null ? null : row.array();
  }

  public java.nio.ByteBuffer bufferForRow() {
    return org.apache.thrift.TBaseHelper.copyBinary(row);
  }

  public TAppend setRow(byte[] row) {
    this.row = row == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(row.clone());
    return this;
  }

  public TAppend setRow(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer row) {
    this.row = org.apache.thrift.TBaseHelper.copyBinary(row);
    return this;
  }

  public void unsetRow() {
    this.row = null;
  }

  /** Returns true if field row is set (has been assigned a value) and false otherwise */
  public boolean isSetRow() {
    return this.row != null;
  }

  public void setRowIsSet(boolean value) {
    if (!value) {
      this.row = null;
    }
  }

  public int getColumnsSize() {
    return (this.columns == null) ? 0 : this.columns.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.nio.ByteBuffer> getColumnsIterator() {
    return (this.columns == null) ? null : this.columns.iterator();
  }

  public void addToColumns(java.nio.ByteBuffer elem) {
    if (this.columns == null) {
      this.columns = new java.util.ArrayList<java.nio.ByteBuffer>();
    }
    this.columns.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.nio.ByteBuffer> getColumns() {
    return this.columns;
  }

  public TAppend setColumns(@org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> columns) {
    this.columns = columns;
    return this;
  }

  public void unsetColumns() {
    this.columns = null;
  }

  /** Returns true if field columns is set (has been assigned a value) and false otherwise */
  public boolean isSetColumns() {
    return this.columns != null;
  }

  public void setColumnsIsSet(boolean value) {
    if (!value) {
      this.columns = null;
    }
  }

  public int getValuesSize() {
    return (this.values == null) ? 0 : this.values.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.nio.ByteBuffer> getValuesIterator() {
    return (this.values == null) ? null : this.values.iterator();
  }

  public void addToValues(java.nio.ByteBuffer elem) {
    if (this.values == null) {
      this.values = new java.util.ArrayList<java.nio.ByteBuffer>();
    }
    this.values.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.nio.ByteBuffer> getValues() {
    return this.values;
  }

  public TAppend setValues(@org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> values) {
    this.values = values;
    return this;
  }

  public void unsetValues() {
    this.values = null;
  }

  /** Returns true if field values is set (has been assigned a value) and false otherwise */
  public boolean isSetValues() {
    return this.values != null;
  }

  public void setValuesIsSet(boolean value) {
    if (!value) {
      this.values = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TABLE:
      if (value == null) {
        unsetTable();
      } else {
        if (value instanceof byte[]) {
          setTable((byte[])value);
        } else {
          setTable((java.nio.ByteBuffer)value);
        }
      }
      break;

    case ROW:
      if (value == null) {
        unsetRow();
      } else {
        if (value instanceof byte[]) {
          setRow((byte[])value);
        } else {
          setRow((java.nio.ByteBuffer)value);
        }
      }
      break;

    case COLUMNS:
      if (value == null) {
        unsetColumns();
      } else {
        setColumns((java.util.List<java.nio.ByteBuffer>)value);
      }
      break;

    case VALUES:
      if (value == null) {
        unsetValues();
      } else {
        setValues((java.util.List<java.nio.ByteBuffer>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TABLE:
      return getTable();

    case ROW:
      return getRow();

    case COLUMNS:
      return getColumns();

    case VALUES:
      return getValues();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TABLE:
      return isSetTable();
    case ROW:
      return isSetRow();
    case COLUMNS:
      return isSetColumns();
    case VALUES:
      return isSetValues();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof TAppend)
      return this.equals((TAppend)that);
    return false;
  }

  public boolean equals(TAppend that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_table = true && this.isSetTable();
    boolean that_present_table = true && that.isSetTable();
    if (this_present_table || that_present_table) {
      if (!(this_present_table && that_present_table))
        return false;
      if (!this.table.equals(that.table))
        return false;
    }

    boolean this_present_row = true && this.isSetRow();
    boolean that_present_row = true && that.isSetRow();
    if (this_present_row || that_present_row) {
      if (!(this_present_row && that_present_row))
        return false;
      if (!this.row.equals(that.row))
        return false;
    }

    boolean this_present_columns = true && this.isSetColumns();
    boolean that_present_columns = true && that.isSetColumns();
    if (this_present_columns || that_present_columns) {
      if (!(this_present_columns && that_present_columns))
        return false;
      if (!this.columns.equals(that.columns))
        return false;
    }

    boolean this_present_values = true && this.isSetValues();
    boolean that_present_values = true && that.isSetValues();
    if (this_present_values || that_present_values) {
      if (!(this_present_values && that_present_values))
        return false;
      if (!this.values.equals(that.values))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTable()) ? 131071 : 524287);
    if (isSetTable())
      hashCode = hashCode * 8191 + table.hashCode();

    hashCode = hashCode * 8191 + ((isSetRow()) ? 131071 : 524287);
    if (isSetRow())
      hashCode = hashCode * 8191 + row.hashCode();

    hashCode = hashCode * 8191 + ((isSetColumns()) ? 131071 : 524287);
    if (isSetColumns())
      hashCode = hashCode * 8191 + columns.hashCode();

    hashCode = hashCode * 8191 + ((isSetValues()) ? 131071 : 524287);
    if (isSetValues())
      hashCode = hashCode * 8191 + values.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TAppend other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetTable(), other.isSetTable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.table, other.table);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetRow(), other.isSetRow());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRow()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.row, other.row);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetColumns(), other.isSetColumns());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColumns()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.columns, other.columns);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetValues(), other.isSetValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.values, other.values);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TAppend(");
    boolean first = true;

    sb.append("table:");
    if (this.table == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.table, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("row:");
    if (this.row == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.row, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("columns:");
    if (this.columns == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.columns, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("values:");
    if (this.values == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.values, sb);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TAppendStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TAppendStandardScheme getScheme() {
      return new TAppendStandardScheme();
    }
  }

  private static class TAppendStandardScheme extends org.apache.thrift.scheme.StandardScheme<TAppend> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAppend struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.table = iprot.readBinary();
              struct.setTableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ROW
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.row = iprot.readBinary();
              struct.setRowIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COLUMNS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list34 = iprot.readListBegin();
                struct.columns = new java.util.ArrayList<java.nio.ByteBuffer>(_list34.size);
                @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem35;
                for (int _i36 = 0; _i36 < _list34.size; ++_i36)
                {
                  _elem35 = iprot.readBinary();
                  struct.columns.add(_elem35);
                }
                iprot.readListEnd();
              }
              struct.setColumnsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list37 = iprot.readListBegin();
                struct.values = new java.util.ArrayList<java.nio.ByteBuffer>(_list37.size);
                @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem38;
                for (int _i39 = 0; _i39 < _list37.size; ++_i39)
                {
                  _elem38 = iprot.readBinary();
                  struct.values.add(_elem38);
                }
                iprot.readListEnd();
              }
              struct.setValuesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAppend struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.table != null) {
        oprot.writeFieldBegin(TABLE_FIELD_DESC);
        oprot.writeBinary(struct.table);
        oprot.writeFieldEnd();
      }
      if (struct.row != null) {
        oprot.writeFieldBegin(ROW_FIELD_DESC);
        oprot.writeBinary(struct.row);
        oprot.writeFieldEnd();
      }
      if (struct.columns != null) {
        oprot.writeFieldBegin(COLUMNS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.columns.size()));
          for (java.nio.ByteBuffer _iter40 : struct.columns)
          {
            oprot.writeBinary(_iter40);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.values != null) {
        oprot.writeFieldBegin(VALUES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.values.size()));
          for (java.nio.ByteBuffer _iter41 : struct.values)
          {
            oprot.writeBinary(_iter41);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAppendTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TAppendTupleScheme getScheme() {
      return new TAppendTupleScheme();
    }
  }

  private static class TAppendTupleScheme extends org.apache.thrift.scheme.TupleScheme<TAppend> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAppend struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTable()) {
        optionals.set(0);
      }
      if (struct.isSetRow()) {
        optionals.set(1);
      }
      if (struct.isSetColumns()) {
        optionals.set(2);
      }
      if (struct.isSetValues()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTable()) {
        oprot.writeBinary(struct.table);
      }
      if (struct.isSetRow()) {
        oprot.writeBinary(struct.row);
      }
      if (struct.isSetColumns()) {
        {
          oprot.writeI32(struct.columns.size());
          for (java.nio.ByteBuffer _iter42 : struct.columns)
          {
            oprot.writeBinary(_iter42);
          }
        }
      }
      if (struct.isSetValues()) {
        {
          oprot.writeI32(struct.values.size());
          for (java.nio.ByteBuffer _iter43 : struct.values)
          {
            oprot.writeBinary(_iter43);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAppend struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.table = iprot.readBinary();
        struct.setTableIsSet(true);
      }
      if (incoming.get(1)) {
        struct.row = iprot.readBinary();
        struct.setRowIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list44 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.columns = new java.util.ArrayList<java.nio.ByteBuffer>(_list44.size);
          @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem45;
          for (int _i46 = 0; _i46 < _list44.size; ++_i46)
          {
            _elem45 = iprot.readBinary();
            struct.columns.add(_elem45);
          }
        }
        struct.setColumnsIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list47 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.values = new java.util.ArrayList<java.nio.ByteBuffer>(_list47.size);
          @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem48;
          for (int _i49 = 0; _i49 < _list47.size; ++_i49)
          {
            _elem48 = iprot.readBinary();
            struct.values.add(_elem48);
          }
        }
        struct.setValuesIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

