package com.Soham.MSProject.Input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CreateInputPairsImpl implements CreateInputPairs 
{  
  private boolean writePairsToFile( String[][] input, File file )
  {
    try
    {
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      for( int i = 0; i < input.length; i++ ) 
      {
        bw.write( input[i][0] ); bw.newLine();
        bw.write( input[i][1] ); bw.newLine(); bw.newLine();
      }
      bw.close();
      fw.close();
      return true;
    } 
    catch (IOException e)
    {
      System.err.println("The input file could not be opened.");
      e.printStackTrace();
      return false;
    }
  }
  
  public String[][] createInputPairs( final String[] seeds ) 
  {
    final int num_seeds = seeds.length;
    String[][] result = new String[num_seeds * (num_seeds - 1) / 2][2];
    int index = 0;
    for( int i = 0; i < (num_seeds - 1); i++ ) 
    {
      for( int j = i + 1; j < num_seeds; j++ )
      {
        result[index][0] = seeds[i];
        result[index][1] = seeds[j];
        index++;
      }
    }
    return result;
  }
  
  private String[] flipSeedStarting( final byte[] seed, final int flips )
      throws UnsupportedEncodingException
  {
    ArrayList<String> list_flipped_strings = new ArrayList< String >( flips + 1 );
    list_flipped_strings.add( new String( seed, "UTF-8" ));
    int flip = flips;
    for( int i = 0; i < (((flips-1) / 8) + 1); i++ )
    {
      int round = (flip > 8) ? 8 : flip;
      for( int j = 0; j < round; j++ )
      {
        byte [] temp = new byte[ seed.length ];
        System.arraycopy( seed, 0, temp, 0, seed.length );
        temp[i] = (byte) (temp[i] ^ (0x80 >> j));
        list_flipped_strings.add( new String( temp, "UTF-8" ));
      }
      flip = flip - 8;
    }
    String [] results = new String[ list_flipped_strings.size() ];    
    for( int i = 0; i < list_flipped_strings.size(); i++ ) {
      results[i] = list_flipped_strings.get( i );
    }
    return results;
  }
  
  private String[] flipSeedMiddle( final byte[] seed, final int flips )
      throws UnsupportedEncodingException
  {
    if( flips % 2 != 0 ) {  //Piggyback the exception.
      throw new UnsupportedEncodingException("Number of flips, for middle flipping should be even.");
    }
    int start_pos = ((seed.length * 8) / 2) - (flips / 2);
    int end_pos = start_pos + flips; // Make sure to have the loop strictly <, not to go over.
    ArrayList<String> list_flipped_strings = new ArrayList< String >( flips + 1 );
    list_flipped_strings.add( new String( seed, "UTF-8" ));
    byte[] combinations = new byte[]{(byte)0x80, (byte)0x40, (byte)0x20, 
        (byte)0x10, (byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};
    for( ; start_pos < end_pos; start_pos++ )
    {
      byte [] temp = new byte[ seed.length ];
      System.arraycopy( seed, 0, temp, 0, seed.length );
      int bytepos = start_pos / 8;  // Find which byte.
      int bitpos  = start_pos % 8;  // Find bit position.
      temp[bytepos] = (byte) (temp[bytepos] ^ combinations[bitpos]);
      list_flipped_strings.add( new String( temp, "UTF-8" ));
    }
    String [] results = new String[ list_flipped_strings.size() ];    
    for( int i = 0; i < list_flipped_strings.size(); i++ ) {
      results[i] = list_flipped_strings.get( i );
    }
    return results;
  }
  
  /**
   * Returns the array of strings with bits toggled from end. For example if a string provided 
   * happens to be byte 10000000 then at flips 2, the return array would be [10000000, 10000001,
   * 10000010]. So the original seed is the head of the list, and the rest follow. If flip is 
   * @param seed The original string, which will be first entry in the return array.
   * @param flips Determines the number of flips to bits and hence the length of return array.
   * If the number of flips is zero, then the list is only the seed string with length one.
   * If the number of flips > number of bits present in seed, then all the bits are flipped.
   * @return Array of string/s that have one bit toggled from the original one. The toggling
   * starts from the end and proceeds towards head.  
   */
  private String[] flipSeedTrailing( final byte[] seed, final int flips )
      throws UnsupportedEncodingException
  {
    ArrayList<String> list_flipped_strings = new ArrayList< String >( flips + 1 );
    list_flipped_strings.add( new String( seed, "UTF-8" ));
    int flip = flips;
    for( int i = 0; i < (((flips-1) / 8) + 1); i++ )
    {
      int round = (flip > 8) ? 8 : flip;
      for( int j = 0; j < round; j++ )
      {
        byte [] temp = new byte[ seed.length ];
        System.arraycopy( seed, 0, temp, 0, seed.length );
        temp[temp.length - (i + 1)] = (byte) (temp[temp.length - (i + 1)] ^ (1 << j));
        list_flipped_strings.add( new String( temp, "UTF-8" ));
      }
      flip = flip - 8;
    }
    String [] results = new String[ list_flipped_strings.size() ];    
    for( int i = 0; i < list_flipped_strings.size(); i++ ) {
      results[i] = list_flipped_strings.get( i );
    }
    return results;
  }
  
  public String[] getFlippedSeeds( final byte[] seed, final String flipend, 
      final int flips ) throws UnsupportedEncodingException
  {
    if( flips < 1 ) { // If flips are just 0, no need to flip anything.
      return new String[]{ new String(seed, "UTF-8") };
    }
    if( seed == null ) {
      throw new UnsupportedEncodingException("The seed for the strings cannot be null.");
    }
    // Make sure that flips are not more than number of bits present.
    int flip = (flips > (seed.length * 8)) ? (seed.length * 8) : flips;
    switch( flipend )
    {
      case "Starting" :
        return flipSeedStarting( seed, flip );
      case "Middle" :
        return flipSeedMiddle( seed, flip );
      case "Trailing" :
        return flipSeedTrailing( seed, flip );
      default :  // If no choice prescribed, flip bits from starting.
        return flipSeedStarting( seed, flip );
    }
  }
  
  public boolean newLinePresent( final String[] flipped_seeds )
  {
    for( String s : flipped_seeds ) {
      if(s.contains("\n")) { return true; }
    }
    return false;
  }
  
  public boolean writeToFile( final String seed, final String flipend, 
      final Integer flips, final File file ) throws UnsupportedEncodingException
  {
    byte[] input;
    try {
      input = seed.getBytes("utf-8");
    } catch( UnsupportedEncodingException uex ) {
      throw uex;
    }
    String [] flipped_seeds = getFlippedSeeds( input, flipend, flips );
    if( !newLinePresent( flipped_seeds ))
    {
      String [][] input_pairs = createInputPairs( flipped_seeds );
      return writePairsToFile( input_pairs, file );
    }
    else 
    {
      System.err.println("The flips gave rise to ");
      return false;
    }
  }
  
  public Object[] checkInputFileOptions( final String seed, final String flip_end,
      final int flips, final String file_name)
  {
    Object [] checked_result = new Object[]{ true, "" } ;
    
    // Make sure all the values are present.
    if ((seed == null) || (flip_end == null) || (file_name == null))
    {
      System.out.println("1");
      checked_result[0] = false;
      checked_result[1] = "Please make sure values for all parameters for "
          + "input file creation are there.";
      return checked_result; // Since preliminary failure return here.
    }
    
    // Make sure the end to flip bits has been specified properly.
    if( flip_end.equals("Starting") || flip_end.equals("Middle") || 
        flip_end.equals("Trailing") ) 
    { /*Do nothing. Everything is fine. Else, make a note of error.*/}
    else
    {
      checked_result[0] = false;
      checked_result[1] += "Please enter which end you want the bits to flip. "
          + "Starting, Middle or Trailing. \n";
    }
    
    // Make sure that flips are in between 0 and 21.
    if ((flips < 1) || (flips > 20))
    {
      checked_result[0] = false;
      checked_result[1] += "The number of flips has to be in between 1 and 20, inclusive. \n";
    }
    
    // Check the file name is not null or not empty.
    if ((file_name == null) || (file_name.equals("")))
    {
      checked_result[0] = false;
      checked_result[1] += "The file name to be created cannot be empty.";
    }
    return checked_result;
  }
  
  public Object[] createFile( final String seed, final String flip_end, 
      final Integer flips, final String file_name )
  {
    Object[] check_result = checkInputFileOptions(seed, flip_end, flips.intValue(), file_name);
    if( !(( Boolean )check_result[0]) ) {
      return check_result;  // If one of the parameters is in error, send error message.
    }
    boolean write_success = false;
    String filename = "";
    try 
    {
      filename = new String(URLEncoder.encode(file_name, "UTF-8")); // Encode any shit for an inappropriate file name.
      filename = filename.endsWith(".txt") ? filename : (filename + ".txt"); // Make sure of its' text format.
      System.out.println("File name is "+ filename);
      File file = new File( filename );  // Create the file object and the file.
      if (!file.exists()) {
        file.createNewFile();
      }
      write_success = writeToFile( seed, flip_end, flips, file );
    }
    catch (UnsupportedEncodingException uex) 
    {
      uex.printStackTrace();
      return (new Object[] { false, "Either input file name is not valid file path.\n"
          + "Or the input string provided could not be encoded." });
    }
    catch( IOException iex )
    {
      iex.printStackTrace();
      return (new Object[] { false, "Input file creation failed. Could not create or write to the file." });
    }
    if( write_success ) {
      return (new Object[] {write_success, "Input file "+ filename +" successfully created."});
    } else {
      return (new Object[] {write_success, "Input file could not be created."});
    }
  }
}