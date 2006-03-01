package org.red5.server.stream.codec;

/*
 * Red5 video codec for the screen capture format. 
 * 
 * @author Joachim Bauch (jojo@struktur.de)
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.ByteBuffer;
import org.red5.server.stream.IVideoStreamCodec;

public class ScreenVideo implements IVideoStreamCodec {

	private Log log = LogFactory.getLog(ScreenVideo.class.getName());
	
	static final byte FLV_FRAME_KEY = 0x10;
	static final byte FLV_CODEC_SCREEN = 0x03;
	
	private byte[] blockData;
	private int[] blockSize;
	private int width;
	private int height;
	private int widthInfo;
	private int heightInfo;
	private int blockWidth;
	private int blockHeight;
	private int blockCount;
	private int blockDataSize;
	
	public ScreenVideo() {
		this.reset();
	}
	
	public void reset() {
		this.blockData = null;
		this.blockSize = null;
		this.width = 0;
		this.height = 0;
		this.widthInfo = 0;
		this.heightInfo = 0;
		this.blockWidth = 0;
		this.blockHeight = 0;
		this.blockCount = 0;
		this.blockDataSize = 0;
	}

	public boolean canHandleData(ByteBuffer data) {
		byte first = data.get();
		boolean result = ((first & 0x0f) == FLV_CODEC_SCREEN);
		data.rewind();
		return result;
	}

	/*
	 * This uses the same algorithm as "compressBound" from zlib
	 */
	private int maxCompressedSize(int size) {
		return size + (size >> 12) + (size >> 14) + 11;
	}
	
	private void updateSize(ByteBuffer data) {
		this.widthInfo = data.getShort();
		this.heightInfo = data.getShort();
		// extract width and height of the frame
		this.width = this.widthInfo & 0xfff;
		this.height = this.heightInfo & 0xfff;
		// calculate size of blocks
		this.blockWidth = ((this.widthInfo >> 12) + 1) << 4;
		this.blockHeight = ((this.heightInfo >> 12) + 1) << 4;
		
		int xblocks = this.width / this.blockWidth;
		if ((this.width % this.blockWidth) != 0)
			// partial block
			xblocks += 1;

		int yblocks = this.height / this.blockHeight;
		if ((this.height % this.blockHeight) != 0)
			// partial block
			yblocks += 1;

		this.blockCount = xblocks * yblocks;
		
		int blockSize = this.maxCompressedSize(this.blockWidth * this.blockHeight * 3);
		if (this.blockDataSize != blockSize) {
			log.info("Allocating memory for " + this.blockCount + " compressed blocks.");
			this.blockDataSize = blockSize;
			this.blockData = new byte[blockSize * this.blockCount];
			this.blockSize = new int[blockSize * this.blockCount];
			// Reset the sizes to zero
			for (int idx=0; idx<this.blockCount; idx++)
				this.blockSize[idx] = 0;
		}
	}
	
	public boolean addData(ByteBuffer data) {
		if (!this.canHandleData(data))
			return false;
		
		data.get();
		this.updateSize(data);
		int idx = 0;
		int pos = 0;
		byte[] tmpData = new byte[this.blockDataSize];
		
		while (data.remaining() > 0) {
			short size = data.getShort();
			if (size == 0) {
				// Block has not been modified
				idx += 1;
				pos += this.blockDataSize;
				continue;
			}
			
			// Store new block data
			this.blockSize[idx] = size;
			data.get(tmpData, 0, (int)size);
			System.arraycopy(tmpData, 0, this.blockData, pos, size);
			idx += 1;
			pos += this.blockDataSize;
		}
		
		data.rewind();
		return true;
	}

	public ByteBuffer getKeyframe() {
		ByteBuffer result = ByteBuffer.allocate(1024);
		result.setAutoExpand(true);

		// Header
		result.put((byte) (FLV_FRAME_KEY | FLV_CODEC_SCREEN));
		
		// Frame size
		result.putShort((short) this.widthInfo);
		result.putShort((short) this.heightInfo);
		
		// Get compressed blocks
		byte[] tmpData = new byte[this.blockDataSize];
		int pos=0;
		for (int idx=0; idx<this.blockCount; idx++) {
			int size = this.blockSize[idx];
			if (size == 0)
				// this should not happen: no data for this block
				return null;
			
			result.putShort((short) size);
			System.arraycopy(this.blockData, pos, tmpData, 0, size);
			result.put(tmpData, 0, size);
			pos += this.blockDataSize;
		}
		
		result.rewind();
		return result;
	}

}
