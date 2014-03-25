package com.Soham.MSProject.SHA3;

import java.math.BigInteger;
import java.util.ArrayList;

public class BLAKE 
{
  public static final byte[][] BLAKE_224_IV = new byte[][]{ 
    new byte[]{ (byte)0xC1, (byte)0x05, (byte)0x9E, (byte)0xD8 },
    new byte[]{ (byte)0x36, (byte)0x7C, (byte)0xD5, (byte)0x07 },
    new byte[]{ (byte)0x30, (byte)0x70, (byte)0xDD, (byte)0x17 },
    new byte[]{ (byte)0xF7, (byte)0x0E, (byte)0x59, (byte)0x39 },
    new byte[]{ (byte)0xFF, (byte)0xC0, (byte)0x0B, (byte)0x31 },
    new byte[]{ (byte)0x68, (byte)0x58, (byte)0x15, (byte)0x11 },
    new byte[]{ (byte)0x64, (byte)0xF9, (byte)0x8F, (byte)0xA7 },
    new byte[]{ (byte)0xBE, (byte)0xFA, (byte)0x4F, (byte)0xA4 }
    };

  public static final byte[][] BLAKE_256_IV = new byte[][]{ 
    new byte[]{ (byte)0x6A, (byte)0x09, (byte)0xE6, (byte)0x67 },
    new byte[]{ (byte)0xBB, (byte)0x67, (byte)0xAE, (byte)0x85 },
    new byte[]{ (byte)0x3C, (byte)0x6E, (byte)0xF3, (byte)0x72 },
    new byte[]{ (byte)0xA5, (byte)0x4F, (byte)0xF5, (byte)0x3A },
    new byte[]{ (byte)0x51, (byte)0x0E, (byte)0x52, (byte)0x7F },
    new byte[]{ (byte)0x9B, (byte)0x05, (byte)0x68, (byte)0x8C },
    new byte[]{ (byte)0x1F, (byte)0x83, (byte)0xD9, (byte)0xAB },
    new byte[]{ (byte)0x5B, (byte)0xE0, (byte)0xCD, (byte)0x19 }
    };
  
  public static final byte[][] BLAKE_256_CONST = new byte[][]{
    new byte[]{ (byte)0x24, (byte)0x3F, (byte)0x6A, (byte)0x88 },
    new byte[]{ (byte)0x85, (byte)0xA3, (byte)0x08, (byte)0xD3 },
    new byte[]{ (byte)0x13, (byte)0x19, (byte)0x8A, (byte)0x2E },
    new byte[]{ (byte)0x03, (byte)0x70, (byte)0x73, (byte)0x44 },
    new byte[]{ (byte)0xA4, (byte)0x09, (byte)0x38, (byte)0x22 },
    new byte[]{ (byte)0x29, (byte)0x9F, (byte)0x31, (byte)0xD0 },
    new byte[]{ (byte)0x08, (byte)0x2E, (byte)0xFA, (byte)0x98 },
    new byte[]{ (byte)0xEC, (byte)0x4E, (byte)0x6C, (byte)0x89 },
    new byte[]{ (byte)0x45, (byte)0x28, (byte)0x21, (byte)0xE6 },
    new byte[]{ (byte)0x38, (byte)0xD0, (byte)0x13, (byte)0x77 },
    new byte[]{ (byte)0xBE, (byte)0x54, (byte)0x66, (byte)0xCF },
    new byte[]{ (byte)0x34, (byte)0xE9, (byte)0x0C, (byte)0x6C },
    new byte[]{ (byte)0xC0, (byte)0xAC, (byte)0x29, (byte)0xB7 },
    new byte[]{ (byte)0xC9, (byte)0x7C, (byte)0x50, (byte)0xDD },
    new byte[]{ (byte)0x3F, (byte)0x84, (byte)0xD5, (byte)0xB5 },
    new byte[]{ (byte)0xB5, (byte)0x47, (byte)0x09, (byte)0x17 }
  };
  
  public static final byte[][] BLAKE_384_IV = new byte[][]{ 
    new byte[]{ (byte)0xCB, (byte)0xBB, (byte)0x9D, (byte)0x5D,
        (byte)0xC1, (byte)0x05, (byte)0x9E, (byte)0xD8 },
    new byte[]{ (byte)0x62, (byte)0x9A, (byte)0x29, (byte)0x2A, 
        (byte)0x36, (byte)0x7C, (byte)0xD5, (byte)0x07 },
    new byte[]{ (byte)0x91, (byte)0x59, (byte)0x01, (byte)0x5A, 
        (byte)0x30, (byte)0x70, (byte)0xDD, (byte)0x17 },
    new byte[]{ (byte)0x15, (byte)0x2F, (byte)0xEC, (byte)0xD8, 
        (byte)0xF7, (byte)0x0E, (byte)0x59, (byte)0x39 },
    new byte[]{ (byte)0x67, (byte)0x33, (byte)0x26, (byte)0x67, 
        (byte)0xFF, (byte)0xC0, (byte)0x0B, (byte)0x31 },
    new byte[]{ (byte)0x8E, (byte)0xB4, (byte)0x4A, (byte)0x87, 
        (byte)0x68, (byte)0x58, (byte)0x15, (byte)0x11 },
    new byte[]{ (byte)0xDB, (byte)0x0C, (byte)0x2E, (byte)0x0D, 
        (byte)0x64, (byte)0xF9, (byte)0x8F, (byte)0xA7 },
    new byte[]{ (byte)0x47, (byte)0xB5, (byte)0x48, (byte)0x1D, 
        (byte)0xBE, (byte)0xFA, (byte)0x4F, (byte)0xA4 }
    };
  
  public static final byte[][] BLAKE_512_IV = new byte[][]{ 
    new byte[]{ (byte)0x6A, (byte)0x09, (byte)0xE6, (byte)0x67, 
        (byte)0xF3, (byte)0xBC, (byte)0xC9, (byte)0x08 },
    new byte[]{ (byte)0xBB, (byte)0x67, (byte)0xAE, (byte)0x85, 
        (byte)0x84, (byte)0xCA, (byte)0xA7, (byte)0x3B },
    new byte[]{ (byte)0x3C, (byte)0x6E, (byte)0xF3, (byte)0x72, 
        (byte)0xFE, (byte)0x94, (byte)0xF8, (byte)0x2B },
    new byte[]{ (byte)0xA5, (byte)0x4F, (byte)0xF5, (byte)0x3A, 
        (byte)0x5F, (byte)0x1D, (byte)0x36, (byte)0xF1 },
    new byte[]{ (byte)0x51, (byte)0x0E, (byte)0x52, (byte)0x7F, 
        (byte)0xAD, (byte)0xE6, (byte)0x82, (byte)0xD1 },
    new byte[]{ (byte)0x9B, (byte)0x05, (byte)0x68, (byte)0x8C, 
        (byte)0x2B, (byte)0x3E, (byte)0x6C, (byte)0x1F },
    new byte[]{ (byte)0x1F, (byte)0x83, (byte)0xD9, (byte)0xAB, 
        (byte)0xFB, (byte)0x41, (byte)0xBD, (byte)0x6B },
    new byte[]{ (byte)0x5B, (byte)0xE0, (byte)0xCD, (byte)0x19, 
        (byte)0x13, (byte)0x7E, (byte)0x21, (byte)0x79 }
    };
  
  public static final byte[][] BLAKE_512_CONST = new byte[][]{
    new byte[]{ (byte)0x24, (byte)0x3F, (byte)0x6A, (byte)0x88, 
        (byte)0x85, (byte)0xA3, (byte)0x08, (byte)0xD3 },
    new byte[]{ (byte)0x13, (byte)0x19, (byte)0x8A, (byte)0x2E,
        (byte)0x03, (byte)0x70, (byte)0x73, (byte)0x44 },
    new byte[]{ (byte)0xA4, (byte)0x09, (byte)0x38, (byte)0x22,
        (byte)0x29, (byte)0x9F, (byte)0x31, (byte)0xD0},
    new byte[]{ (byte)0x08, (byte)0x2E, (byte)0xFA, (byte)0x98,
        (byte)0xEC, (byte)0x4E, (byte)0x6C, (byte)0x89 },
    new byte[]{ (byte)0x45, (byte)0x28, (byte)0x21, (byte)0xE6,
        (byte)0x38, (byte)0xD0, (byte)0x13, (byte)0x77 },
    new byte[]{ (byte)0xBE, (byte)0x54, (byte)0x66, (byte)0xCF,
        (byte)0x34, (byte)0xE9, (byte)0x0C, (byte)0x6C },
    new byte[]{ (byte)0xC0, (byte)0xAC, (byte)0x29, (byte)0xB7,
        (byte)0xC9, (byte)0x7C, (byte)0x50, (byte)0xDD },
    new byte[]{ (byte)0x3F, (byte)0x84, (byte)0xD5, (byte)0xB5,
        (byte)0xB5, (byte)0x47, (byte)0x09, (byte)0x17 },
    new byte[]{ (byte)0x92, (byte)0x16, (byte)0xD5, (byte)0xD9,
        (byte)0x89, (byte)0x79, (byte)0xFB, (byte)0x1B },
    new byte[]{ (byte)0xD1, (byte)0x31, (byte)0x0B, (byte)0xA6,
        (byte)0x98, (byte)0xDF, (byte)0xB5, (byte)0xAC },
    new byte[]{ (byte)0x2F, (byte)0xFD, (byte)0x72, (byte)0xDB,
        (byte)0xD0, (byte)0x1A, (byte)0xDF, (byte)0xB7 },
    new byte[]{ (byte)0xB8, (byte)0xE1, (byte)0xAF, (byte)0xED,
        (byte)0x6A, (byte)0x26, (byte)0x7E, (byte)0x96 },
    new byte[]{ (byte)0xBA, (byte)0x7C, (byte)0x90, (byte)0x45,
        (byte)0xF1, (byte)0x2C, (byte)0x7F, (byte)0x99 },
    new byte[]{ (byte)0x24, (byte)0xA1, (byte)0x99, (byte)0x47,
        (byte)0xB3, (byte)0x91, (byte)0x6C, (byte)0xF7 },
    new byte[]{ (byte)0x08, (byte)0x01, (byte)0xF2, (byte)0xE2,
        (byte)0x85, (byte)0x8E, (byte)0xFC, (byte)0x16 },
    new byte[]{ (byte)0x63, (byte)0x69, (byte)0x20, (byte)0xD8,
        (byte)0x71, (byte)0x57, (byte)0x4E, (byte)0x69 }
  };
  
  public static final int[][] SIGMA = new int[][]{
    new int[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
    new int[]{ 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 },
    new int[]{ 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4 },
    new int[]{ 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 },
    new int[]{ 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13 },
    new int[]{ 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 },
    new int[]{ 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11 },
    new int[]{ 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10 },
    new int[]{ 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5 },
    new int[]{ 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0 }
  };
  
  // Source: http://stackoverflow.com/questions/13185073/convert-a-string-to-byte-array
  /**
   * This method converts the string that is in hex format, to its equivalent byte format.
   * For example the string AB is converted to byte format 1010 1011.
   * @param msg in hex format representation of bytes
   * @return array list of bytes
   */
  public ArrayList<Byte> convertHexStringToBytes( String msg )
  {
    int length = msg.length();
    ArrayList<Byte> message = new ArrayList<>(msg.length() / 2);
    for( int i = 0; i < length; i += 2 )
    {
      message.add((byte)((Character.digit(msg.charAt(i), 16) << 4) +
          Character.digit(msg.charAt(i + 1), 16)));
    }
    return message;
  }
  
  public void padHelper( ArrayList<Byte> msg )
  {
    msg.add(( byte )0x80);
  }
  
  public ArrayList<Byte> padding( ArrayList<Byte> msg, int word_size )
  {
    byte[] padding_bytes = new byte[]{ (byte)0x80, (byte)0x00, (byte)0x01, (byte)0x81 };
    int bytes_append = msg.size()  % 64;
    if( bytes_append < 56 )
    {
      if( bytes_append <= 54 )
      {
        msg.add( padding_bytes[0] );
        int zero_bytes = 56 - (bytes_append + 2);
        for( int i = 0; i < zero_bytes; i++ ) {
          msg.add( padding_bytes[1] );
        }
        msg.add( padding_bytes[2] );
      }
      else { // bytes_append == 55
        msg.add( padding_bytes[3] );
      }
    }
    else
    {
      msg.add( padding_bytes[0] );
      bytes_append = (msg.size() % 64) + 55;
      for( int i = 0; i < bytes_append; i++ ) {
        msg.add( padding_bytes[1] );
      }
      msg.add( padding_bytes[2] );
    }
    int length = msg.size() * 8; // For message of size l bits
    BigInteger bi = BigInteger.valueOf( length );
    byte[] temp_num_bitlength = bi.toByteArray();
    byte[] byte_num_bitlength = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    for( int i = 0; i < temp_num_bitlength.length; i++ ) 
    {
      byte_num_bitlength[ byte_num_bitlength.length - 1 - i ] |= 
          temp_num_bitlength[temp_num_bitlength.length - 1 - i ]; 
    }
    for( byte b : byte_num_bitlength ) {
      msg.add(b);
    }
    return msg;
  }
  
  public byte[] hash( String msg, int op_size )
  {
    ArrayList<Byte> message = convertHexStringToBytes( msg );
    switch( op_size )
    {
    case 224:
    case 256:
      BLAKE256 blake256 = new BLAKE256();
      blake256.hash( message );
      message = padding( message, 32 );
      if( 0 == ((message.size() * 8) % 512)) {
        System.err.println("Error in padding.");
      }
      // The word size here is 32 bits.
      break;
    case 384:
    case 512:
      padding( message, 64 );
      break;
    default:  // By default the output size would be same as that of the 
      padding( message, 64 );
    }
    return new byte[]{0x00};
  }
  
  public static void main( String[] args )
  {}
}