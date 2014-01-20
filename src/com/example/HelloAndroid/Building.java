package com.example.HelloAndroid;

/**
 * User: lockersoft
 * Date: 1/18/14
 * Time: 10:53 PM
 */
public class Building {
  public int x,y;
  public String name;

  public Building( String _name, int _x, int _y )
  {
    name = _name;
    x = _x;
    y = _y;
  }
  public String toString()
  {
    return( name + " (" + x + "," + y + ")" );
  }

}
