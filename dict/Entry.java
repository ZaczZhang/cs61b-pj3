/* Entry.java */

package dict;

/**
 *  A class for dictionary entries.
 *
 *  DO NOT CHANGE THIS FILE.  It is part of the interface of the
 *  Dictionary ADT.
 **/

public class Entry {

    protected Object key;
    protected Object value;

    /**
       key() returns the key.
       @params: none
       @return: the key of the Entry.
    **/
    public Object key() {
        return key;
    }

    /**
       value() returns the value.
       @params: none
       @return: the value of the Entry.
    **/
    public Object value() {
        return value;
    }

}
