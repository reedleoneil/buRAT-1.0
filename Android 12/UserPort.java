class UserPort {
  public native void outport(int portid, int value);
  public native void outportb(int portid, byte value);
  public native byte inportb(int portid);
  public native int inport(int portid);
  static {
    System.loadLibrary("UserPortImp");
  }
}

