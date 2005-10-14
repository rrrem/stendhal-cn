/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.tools.tiled;

import tiled.core.*;
import tiled.io.*;
import java.util.Stack;
import java.util.Iterator;
import java.io.*;

/** Writer Plugin for tiled. Saves maps as *.stend files */
public class StendhalMapWriter implements MapWriter
{
  /**
   * Method writeMap
   * @param map
   * @param filename
   *
   * @throws Exception
   */
  public void writeMap(Map map, String filename) throws Exception
  {
    
    File file = new File(filename);
    Iterator ml = map.getLayers();
    while (ml.hasNext())
    {
      MapLayer layer=(MapLayer)ml.next();
      String stendFile = file.getParent()+File.separator+layer.getName()+".stend";
      System.out.println("writing "+stendFile);
      
      FileOutputStream os = new FileOutputStream(stendFile);
      PrintWriter writer = new PrintWriter(os);
      
      writer.println(layer.getWidth()+" "+layer.getHeight());
      
      for (int y = 0; y < layer.getHeight(); y++)
      {
        for (int x = 0; x < layer.getWidth(); x++)
        {
          Tile tile = ((TileLayer)layer).getTileAt(x, y);
          int gid = 0;
          
          if (tile != null)
          {
            gid = tile.getGid();
          }
          
          //writer.format("%1$3d"+((x==layer.getWidth()-1)?"":":"),gid);
          writer.print(gid+((x==layer.getWidth()-1)?"":","));
        }
        
        writer.println();
      }
      
      writer.println();
      writer.close();
    }
    
    // TODO: Add your code here
  }
  
  /**
   * Method writeTileset
   *
   *
   * @param set
   * @param filename
   *
   * @throws Exception
   *
   */
  public void writeTileset(TileSet set, String filename) throws Exception
  {
    // TODO: Add your code here
  }
  
  /**
   * Method writeMap
   *
   *
   * @param map
   * @param out
   *
   * @throws Exception
   *
   */
  public void writeMap(Map map, OutputStream out) throws Exception
  {
    // TODO: Add your code here
  }
  
  /**
   * Method writeTileset
   *
   *
   * @param set
   * @param out
   *
   * @throws Exception
   *
   */
  public void writeTileset(TileSet set, OutputStream out) throws Exception
  {
    // TODO: Add your code here
  }
  
  public boolean accept(File pathname)
  {
    try
    {
      String path = pathname.getCanonicalPath().toLowerCase();
      if (path.endsWith(".stend"))
      {
        return true;
      }
    }
    catch (IOException e)
    {}
    return false;
  }
  
  public String getFilter() throws Exception
  {
    return "*.stend";
  }
  
  public String getName()
  {
    return "Stendhal Writer";
  }
  
  public String getDescription()
  {
    return "+---------------------------------------------+\n" +
            "|      An experimental writer for Stendhal    |\n" +
            "|                                             |\n" +
            "|      (c) Miguel Angel Blanch Lardin 2005    |\n" +
            "|                                             |\n" +
            "+---------------------------------------------+";
  }
  
  public String getPluginPackage()
  {
    return "Stendhal Reader/Writer Plugin";
  }
  
  public void setErrorStack(Stack es)
  {
    // TODO: implement setErrorStack
  }
}
