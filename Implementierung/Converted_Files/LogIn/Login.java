package LogIn;

import remote;

/**
 *
 */
public interface LogIn extends remote {


  /**
   * @param s
   * @param pw
   * @return
   */
  public boolean anmelden(String s, String pw);

  /**
   * @param s
   * @param pw
   * @param pw2
   * @return
   */
  public boolean registrieren(String s, String pw, String pw2);

  /**
   *
   */
  public void weiterleiten();

}