package com.uw.hw6b.gui;


import java.awt.EventQueue;

public final class DijkstraMain
{

  /**
   * Private constructor to inhibit instantiation.
   */
  private DijkstraMain() 
  {
    throw new IllegalStateException();
  }

  /**
   * Start point for the program.
   * 
   * @param the_args command line arguments - ignored
   */
  public static void main(final String... the_args) 
  {

    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() 
      {
          new DijkstraGUI(); // create the graphical user interface
      }
    });
  }

}
