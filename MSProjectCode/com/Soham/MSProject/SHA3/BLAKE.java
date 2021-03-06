package com.Soham.MSProject.SHA3;

import java.nio.ByteBuffer;

public class BLAKE implements Hash
{
  public static final int[] IV224 = { 0xC1059ED8, 0x367CD507, 0x3070DD17, 0xF70E5939, 0xFFC00B31,
    0x68581511, 0x64F98FA7, 0xBEFA4FA4 };
  
  public static final int[] IV256 = { 0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 0x510E527F,
    0x9B05688C, 0x1F83D9AB, 0x5BE0CD19 };
  
  public static final int[] CONST256 = { 0x243F6A88, 0x85A308D3, 0x13198A2E, 0x03707344, 
    0xA4093822, 0x299F31D0, 0x082EFA98, 0xEC4E6C89, 0x452821E6, 0x38D01377, 0xBE5466CF,
    0x34E90C6C, 0xC0AC29B7, 0xC97C50DD, 0x3F84D5B5, 0xB5470917 };
  
  public static final long[] IV384 = { 0xCBBB9D5DC1059ED8L, 0x629A292A367CD507L, 
    0x9159015A3070DD17L, 0x152FECD8F70E5939L, 0x67332667FFC00B31L, 0x8EB44A8768581511L,
    0xDB0C2E0D64F98FA7L, 0x47B5481DBEFA4FA4L };
  
  public static final long[] IV512 = { 0x6A09E667F3BCC908L, 0xBB67AE8584CAA73BL, 
    0x3C6EF372FE94F82BL, 0xA54FF53A5F1D36F1L, 0x510E527FADE682D1L, 0x9B05688C2B3E6C1FL,
    0x1F83D9ABFB41BD6BL, 0x5BE0CD19137E2179L };
  
  public static final long[] CONST512 = { 0x243F6A8885A308D3L, 0x13198A2E03707344L,
    0xA4093822299F31D0L, 0x082EFA98EC4E6C89L, 0x452821E638D01377L, 0xBE5466CF34E90C6CL,
    0xC0AC29B7C97C50DDL, 0x3F84D5B5B5470917L, 0x9216D5D98979FB1BL, 0xD1310BA698DFB5ACL,
    0x2FFD72DBD01ADFB7L, 0xB8E1AFED6A267E96L, 0xBA7C9045F12C7F99L, 0x24A19947B3916CF7L,
    0x0801F2E2858EFC16L, 0x636920D871574E69L };  

  public static final int[][] SIGMA = new int[][] {
    { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
    { 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 },
    { 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4 },
    { 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 },
    { 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13 },
    { 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 },
    { 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11 },
    { 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10 },
    { 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5 },
    { 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0 }
  };
  
  /**
   * This method converts the string that is in hex format, to its equivalent byte format.
   * For example the string AB is converted to byte format 1010 1011.
   * @param msg in hex format representation of bytes
   * @return array list of bytes
   */
  //Source: http://stackoverflow.com/questions/13185073/convert-a-string-to-byte-array
  public byte[] convertHexStringToBytes( String msg )
  {
    int length = msg.length();
    byte[] message = new byte[msg.length() / 2];
    for( int i = 0; i < length; i += 2 )
    {
      message[i / 2] = (byte)((Character.digit(msg.charAt(i), 16) << 4) +
          Character.digit(msg.charAt(i + 1), 16));
    }
    return message;
  }
  
  /**
   * In the case when more than one byte needs to be padded.
   * @param pad_msg
   * @param msg_len
   * @param digest_len
   * @param bit_len This is the 32 or the 64 bit representation of l bits, in the message.
   * @param msg
   * @return
   */
  public byte[] padHelper2( byte[] pad_msg, int msg_len, int digest_len, byte[] bit_len,
      byte[] msg )
  {
    for( int i = 0; i < msg_len; i++ ) {
      pad_msg[i] = msg[i];
    }
    pad_msg[msg_len] = ( byte )0x80;
    int zero_index = pad_msg.length - (((384 == digest_len) || (512 == digest_len)) ? 17 : 9);
    for( int i = msg_len + 1; i < zero_index; i++ ) {
      pad_msg[i] = ( byte )0x00;
    }
    pad_msg[zero_index] = ((512 == digest_len) || (256 == digest_len)) ? (byte)0x01 : (byte)0x00;
    int word_len = ((384 == digest_len) || (512 == digest_len)) ? 16 : 8;
    for( int i = 0; i < word_len; i++ ) {
      pad_msg[pad_msg.length - word_len + i] = bit_len[i];
    }
    return pad_msg;
  }
  
  /**
   * Handle the special case in padding of just one more byte to be added for padding.
   * @param message
   * @param bit_len the number of bits in the message.
   * @param digest_length
   * @return
   */
  public byte[] padHelper1( byte[] message, byte[] bit_len, int digest_length )
  {
    int pad_len = ((224 == digest_length) || (256 == digest_length)) ? 9 : 17;
    byte[] pad_msg = new byte[message.length + pad_len];
    for( int i = 0; i < message.length; i++ ) {
      pad_msg[i] = message[i];
    }
    pad_msg[message.length] = ((224 == digest_length) || (384 == digest_length)) ? 
        (byte)0x80 : (byte)0x81;
    for( int i = 0; i < (pad_len - 1); i++ ) {
      pad_msg[message.length + 1 + i] = bit_len[i];
    }
    return pad_msg;
  }
  
  /**
   * Handle the padding for 224 and 256 digest length. The message bit length is long format.
   * @param message
   * @param digest_length
   * @return
   */
  public byte[] pad256( byte[] message, int digest_length )
  {
    if( !((224 == digest_length) || (256 == digest_length))) { return null; }
    long msg_bit_len = message.length * 8;
    ByteBuffer buf = ByteBuffer.allocate(8);
    buf.putLong( msg_bit_len );
    byte[] bit_len = buf.array(); // <l> length of message in bits, in 64 bit format.
    int bits_remaining = ( int )(msg_bit_len % 512); // For our applications message length is small.
    if( 440 == bits_remaining ) { // Only byte 0x81 needs to append and <l>64
      return padHelper1( message, bit_len, digest_length );
    }
    if( bits_remaining > 440 )
    {
      byte[] pad_msg = new byte[ message.length + 64 + (64 - (bits_remaining / 8)) ];
      return padHelper2( pad_msg, message.length, digest_length, bit_len, message );
    }
    else // bits_remaining < 440
    {
      byte[] pad_msg = new byte[message.length + ((512 - bits_remaining) / 8)];
      return padHelper2( pad_msg, message.length, digest_length, bit_len, message );
    }
  }
  
  /**
   * Pad for digest length of 384 and 512. The message bit len is kept in long since the message
   * format is hardly going to cross the threshold of 2 ^ 64.
   * @param message
   * @param digest_length
   * @return
   */
  public byte[] pad512( byte[] message, int digest_length )
  {
    if( !((384 == digest_length) || (512 == digest_length)) ){ return null; }
    long msg_bit_len = message.length * 8;   // For our purpose the message is not around 1 ^ 128.
    ByteBuffer buf = ByteBuffer.allocate(8);
    buf.putLong( msg_bit_len );
    byte[] msg_bit_len_arr = buf.array();
    byte[] bit_len = new byte[16];
    for( int i = 0; i < 16; i++ ) { bit_len[i] = 0x00; }
    for( int i = 7; i >= 0; i-- ) {
      bit_len[ 8 + i ] = msg_bit_len_arr[i];
    }
    int bits_remaining = (message.length * 8) % 1024;
    if( 888 == bits_remaining ) {
      return padHelper1( message, bit_len, digest_length );
    }
    if( bits_remaining < 888 )
    {
      byte[] pad_msg = new byte[message.length + ((1024 - bits_remaining) / 8)];
      return padHelper2( pad_msg, message.length, digest_length, bit_len, message );
    }
    else // bits_remaining > 888
    {
      byte[] pad_msg = new byte[message.length + 128 + ((1024 - bits_remaining) / 8)];
      return padHelper2( pad_msg, message.length, digest_length, bit_len, message );
    }
  }
  
  /**
   * Convert the bytes in message to 4 word block of int for processing in the matrix.
   * @param padded_msg
   * @return
   */
  public int[][] getBlocks32Word( byte[] padded_msg )
  {
    int rows = padded_msg.length / 64;
    int [][] blocks = new int[rows][16];
    int k = 0;
    ByteBuffer wrapped;
    for( int i = 0; i < rows; i++ )
    {
      for( int j = 0; j < 16; j++ )
      {
        k = (i * 64) + (j * 4);
        byte[] arr = {padded_msg[k], padded_msg[k + 1], padded_msg[k + 2], padded_msg[k + 3]};
        wrapped = ByteBuffer.wrap( arr );
        blocks[i][j] = wrapped.getInt();
      }
    }
    return blocks;
  }
  
  /**
   * Convert the bytes to long form for input to the state.
   * @param padded_msg
   * @return
   */
  public long[][] getBlocks64Word( byte[] padded_msg )
  {
    int rows = padded_msg.length / 128;
    long [][] blocks = new long[rows][16];
    int k = 0;
    ByteBuffer wrapped;
    for( int i = 0; i < rows; i++ )
    {
      for( int j = 0; j < 16; j++ )
      {
        k = (i * 128) + (j * 8);
        byte[] arr = {padded_msg[k], padded_msg[k + 1], padded_msg[k + 2], padded_msg[k + 3],
            padded_msg[k + 4], padded_msg[k + 5], padded_msg[k + 6], padded_msg[k + 7]};
        wrapped = ByteBuffer.wrap( arr );
        blocks[i][j] = wrapped.getLong();
      }
    }
    return blocks;
  }
  
  /**
   * The G function for the digest lengths of 224 and 256.
   * @param a v0
   * @param b v1
   * @param c v2
   * @param d v3
   * @param state that has previous hash, counters, and salt.
   * @param index G_i that is the ith representation.
   * @param msg the int[16] block containing message.
   * @param round the iteration of the compression.
   */
  public void g32( int a, int b, int c, int d, int[] state, int index, int[] msg, int round )
  {
    int sig1 = SIGMA[round % 10][2 * index];
    int sig2 = SIGMA[round % 10][(2 * index) + 1];
    int temp;
    // a ← a + b + (mσr (2i) ⊕ cσr (2i+1) )
    state[a] = state[a] + state[b] + (msg[sig1] ^ CONST256[sig2]); 
    temp     = state[d] ^ state[a];
    state[d] = (temp >>> 16) | (temp << 16);                 // d ← (d ⊕ a) >>> 16
    state[c] = state[c] + state[d];                          // c ← c+d
    temp     = state[b] ^ state[c];
    state[b] = (temp >>> 12) | (temp << 20);                 // b ← (b ⊕ c) >>> 12
    // a ← a + b + (mσr (2i+1) ⊕ cσr (2i))
    state[a] = state[a] + state[b] + (msg[sig2] ^ CONST256[sig1]);
    temp = state[d] ^ state[a];
    state[d] = (temp >>> 8) | (temp << 24);                  // d ← (d ⊕ a) >>> 8
    state[c] = state[c] + state[d];                          // c ← c+d
    temp     = state[b] ^ state[c];
    state[b] = (temp >>> 7) | (temp << 25);                  // b ← (b ⊕ c) >>> 7
  }
  
  /**
   * Sets up the message for compression and stiches together the hashes. The counter here is 
   * only operated with 12, 13 position in state since the message is not going to be 64 bits.
   * There is no salt so no XOR with zeros.
   * @param pre_state the initial value for respective digest lengths.
   * @param block The message broken into blocks.
   * @param counter The number of bits in the message.
   * @param rounds The rounds for compression.
   * @return int[16] state from which to extract hash
   */
  public int[] compress32( int[] pre_state, int[] block, int counter, int rounds )
  {
    int [] state = new int[16];
    state[0] = pre_state[0]; state[1] = pre_state[1]; 
    state[2] = pre_state[2]; state[3] = pre_state[3];
    state[4] = pre_state[4]; state[5] = pre_state[5]; 
    state[6] = pre_state[6]; state[7] = pre_state[7];
    // Salt is not there, no use of XOR with zero, so just assign the values.
    state[8]  = CONST256[0];  state[9]  = CONST256[1]; 
    state[10] = CONST256[2];  state[11] = CONST256[3];
    // Our message is not above 2^32 bits, use int for the counter. The last 2 do not need counter.
    state[12] = CONST256[4] ^ counter; state[13] = CONST256[5] ^ counter;
    state[14] = CONST256[6];           state[15] = CONST256[7];
    for( int i = 0; i < rounds; i++ )
    {
      g32( 0, 4, 8,  12, state, 0, block, i );  //G0 (v0 , v4 , v8  , v12 )
      g32( 1, 5, 9,  13, state, 1, block, i );  //G1 (v1 , v5 , v9  , v13 )
      g32( 2, 6, 10, 14, state, 2, block, i );  //G2 (v2 , v6 , v10 , v14 )
      g32( 3, 7, 11, 15, state, 3, block, i );  //G3 (v3 , v7 , v11 , v15 )
      g32( 0, 5, 10, 15, state, 4, block, i );  //G4 (v0 , v5 , v10 , v15 )
      g32( 1, 6, 11, 12, state, 5, block, i );  //G5 (v1 , v6 , v11 , v12 )
      g32( 2, 7, 8,  13, state, 6, block, i );  //G6 (v2 , v7 , v8  , v13 )
      g32( 3, 4, 9,  14, state, 7, block, i );  //G7 (v3 , v4 , v9  , v14 )
    }
    int[] finalised = new int[8]; // None of them are salted.
    for( int i = 0; i < 8; i++ ) {
      finalised[i] = pre_state[i] ^ state[i] ^ state[i + 8];
    }
    return finalised;
  }
  
  /**
   * Set up the counter and call compression on all message blocks.
   * @param blocks
   * @param rounds
   * @param msg_len
   * @param digest_len
   * @return int[16] from which hash to be squeezed.
   */
  public int[] transform32( int[][] blocks, int rounds, int msg_len, int digest_len )
  {
    int[] counter = new int[ blocks.length ]; // You will need as many counters as many blocks.
    if((msg_len % 512) > 440) 
    {
      counter[counter.length - 1] = 0;
      counter[counter.length - 2] = msg_len;
    }
    else
    {
      counter[counter.length - 1] = msg_len;
      if(counter.length > 1){ counter[counter.length - 2] = 512; }
    }
    if( counter.length > 2 )
    {
      int bit_counter = 0;
      for( int i = 0; i < (counter.length - 2); i++ ) 
      {
        bit_counter += 512;
        counter[i] = bit_counter;
      }
    }
    int[] state = (224 == digest_len) ? IV224 : IV256;
    rounds = (0 == rounds) ? 14 : rounds;
    for( int i = 0; i < blocks.length ; i++ ) {
      state = compress32( state, blocks[i], counter[i], rounds );
    }
    return state;
  }
  
  /**
   * The G function for the digest lengths of 384 and 512. The state is being updated by reference.
   * @param a v0
   * @param b v1
   * @param c v2
   * @param d v3
   * @param state that has previous hash, counters, and salt.
   * @param index G_i that is the ith representation.
   * @param msg the long[16] block containing message.
   * @param round the iteration of the compression.
   */
  public void g64( int a, int b, int c, int d, long[] state, int index, long[] msg, int round )
  {
    int sig1 = SIGMA[round % 10][2 * index];
    int sig2 = SIGMA[round % 10][(2 * index) + 1];
    long temp;
    // a ← a + b + (mσr (2i) ⊕ cσr (2i+1))
    state[a] = state[a] + state[b] + (msg[sig1] ^ CONST512[sig2]);
    temp     = state[d] ^ state[a];
    state[d] = (temp >>> 32) | (temp << 32);                   // d ← (d ⊕ a) >>> 32
    state[c] = state[c] + state[d];                            // c ← c+d
    temp     = state[b] ^ state[c];
    state[b] = (temp >>> 25) | (temp << 39);                   // b ← (b ⊕ c) >>> 25
    //a ← a + b + (mσr (2i+1) ⊕ cσr (2i) )
    state[a] = state[a] + state[b] + (msg[sig2] ^ CONST512[sig1]);
    temp     = state[d] ^ state[a];
    state[d] = (temp >>> 16) | (temp << 48);                   // d ← (d ⊕ a) >>> 16
    state[c] = state[c] + state[d];                            // c ← c+d
    temp     = state[b] ^ state[c];
    state[b] = (temp >>> 11) | (temp << 53);                   // b ← (b ⊕ c) >>> 11
  }
  
  /**
   * Sets up the message for compression and stiches together the hashes. The counter here is 
   * only operated with 12, 13 position in state since the message is not going to be 128 bits.
   * There is no salt so no XOR with zeros.
   * @param pre_state the initial value for respective digest lengths.
   * @param block The message broken into blocks.
   * @param counter the number of bits in the message.
   * @param rounds the rounds for compression.
   * @return long[16] state from which to extract hash
   */
  public long[] compress64( long[] pre_state, long[] block, long counter, int rounds )
  {
    long [] state = new long[16];
    state[0] = pre_state[0]; state[1] = pre_state[1]; 
    state[2] = pre_state[2]; state[3] = pre_state[3];
    state[4] = pre_state[4]; state[5] = pre_state[5]; 
    state[6] = pre_state[6]; state[7] = pre_state[7];
    // Salt is not there, no use of XOR with zero, so just assign the values.
    state[8]  = CONST512[0]; state[9]  = CONST512[1]; 
    state[10] = CONST512[2]; state[11] = CONST512[3];
    // Our message is not above 2^32 bits, so just use long for counter. The last 2 do not need it.
    state[12] = CONST512[4] ^ counter; state[13] = CONST512[5] ^ counter;
    state[14] = CONST512[6];           state[15] = CONST512[7];
    for( int i = 0; i < rounds; i++ )
    {
      g64( 0, 4, 8,  12, state, 0, block, i );  //G0 (v0 , v4 , v8  , v12 )
      g64( 1, 5, 9,  13, state, 1, block, i );  //G1 (v1 , v5 , v9  , v13 )
      g64( 2, 6, 10, 14, state, 2, block, i );  //G2 (v2 , v6 , v10 , v14 )
      g64( 3, 7, 11, 15, state, 3, block, i );  //G3 (v3 , v7 , v11 , v15 )
      g64( 0, 5, 10, 15, state, 4, block, i );  //G4 (v0 , v5 , v10 , v15 )
      g64( 1, 6, 11, 12, state, 5, block, i );  //G5 (v1 , v6 , v11 , v12 )
      g64( 2, 7, 8,  13, state, 6, block, i );  //G6 (v2 , v7 , v8  , v13 )
      g64( 3, 4, 9,  14, state, 7, block, i );  //G7 (v3 , v4 , v9  , v14 )
    }
    long[] finalised = new long[8]; // None of them are salted.
    for( int i = 0; i < 8; i++ ) {
      finalised[i] = pre_state[i] ^ state[i] ^ state[i + 8];
    }
    return finalised;
  }
  
  /**
   * Set up the counters and call the compression on each of the message blocks.
   * @param blocks
   * @param rounds
   * @param msg_len
   * @param digest_len
   * @return long[16] form which to squeeze bytes for final hash.
   */
  public long[] transform64( long[][] blocks, int rounds, int msg_len, int digest_len )
  {
    rounds = (0 == rounds) ? 16 : rounds;
    long[] counter = new long[ blocks.length ];
    if((msg_len % 1024) > 888) 
    {
      counter[counter.length - 1] = 0;
      counter[counter.length - 2] = msg_len;
    }
    else
    {
      counter[counter.length - 1] = msg_len;
      if(counter.length > 1){ counter[counter.length - 2] = 1024; }
    }
    if( counter.length > 2 )
    {
      long bit_counter = 0;
      for( int i = 0; i < (counter.length - 2); i++ ) 
      {
        bit_counter += 1024;
        counter[i] = bit_counter;
      }
    }
    long[] state = (384 == digest_len) ? IV384 : IV512;
    for( int i = 0; i < blocks.length ; i++ ) {
      state = compress64( state, blocks[i], counter[i], rounds );
    }
    return state;
  }
  
  /**
   * Get the bytes from int array for the final hash digest.
   * @param hashed
   * @param digest_len
   * @return byte[digest_len / 8] the hash
   */
  public byte[] squeezeBytesInt( int[] hashed, int digest_len )
  {
    int end_point = digest_len / 32;
    byte[] hash = new byte[digest_len / 8];
    for( int i = 0; i < end_point; i++ )
    {
      ByteBuffer buf = ByteBuffer.allocate(4);
      buf.putInt( hashed[i] );
      byte[] arr = buf.array();
      for( int j = 0; j < 4; j++ ) {
        hash[(i * 4) + j] = arr[j];
      }
    }
    return hash;
  }
  
  /**
   * Squeeze byte from long[16] for the hash digest.
   * @param hashed
   * @param digest_len
   * @return byte[digest_len / 8]
   */
  public byte[] squeezeBytesLong( long[] hashed, int digest_len )
  {
    int end_point = digest_len / 64;
    byte[] hash = new byte[digest_len / 8];
    for( int i = 0; i < end_point; i++ )
    {
      ByteBuffer buf = ByteBuffer.allocate(8);
      buf.putLong( hashed[i] );
      byte[] arr = buf.array();
      for( int j = 0; j < 8; j++ ) {
        hash[(i * 8) + j] = arr[j];
      }
    }
    return hash;
  }
  
  /**
   * Hash a message based on the digest lengths and the number of compression rounds. Compression
   * rounds will be normal if supplied with zero else they will compress the number provided.
   * @param msg
   * @param digest_length
   * @param rounds
   * @return
   */
  public byte[] hash( String msg, int digest_length, int rounds )
  {
    byte [] message = convertHexStringToBytes( msg );
    byte [] padded_msg;
    byte [] hash;
    int  [] hash32;
    long [] hash64;
    int  [][] blocks32bit;
    long [][] blocks64bit;
    switch( digest_length )
    {
    case 224:
      padded_msg = pad256( message, digest_length );
      blocks32bit = getBlocks32Word( padded_msg );
      hash32 = transform32( blocks32bit, rounds, message.length * 8, digest_length );
      hash = squeezeBytesInt( hash32, digest_length );
      return hash;
    case 256:
      padded_msg = pad256( message, digest_length );
      blocks32bit = getBlocks32Word( padded_msg );
      hash32 = transform32( blocks32bit, rounds, message.length * 8, digest_length );
      hash = squeezeBytesInt( hash32, digest_length );
      return hash;
    case 384:
      padded_msg = pad512( message, digest_length );
      blocks64bit = getBlocks64Word( padded_msg );
      hash64 = transform64( blocks64bit, rounds, message.length * 8, digest_length );
      hash = squeezeBytesLong( hash64, digest_length );
      return hash;
    case 512:
      padded_msg = pad512( message, digest_length );
      blocks64bit = getBlocks64Word( padded_msg );
      hash64 = transform64( blocks64bit, rounds, message.length * 8, digest_length );
      hash = squeezeBytesLong( hash64, digest_length );
      return hash;
    }
    return null;
  }
  
  public static void main( String[] args )
  {
    BLAKE b = new BLAKE();
    byte[] digest = b.hash("54686520717569636B2062726F776E20666F78206A756D7073206F76657220746"
        + "865206C617A7920646F67", 512, 0);
    for( byte d : digest ) {
      System.out.printf("%02X", d);
    }
  }
}