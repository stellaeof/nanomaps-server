package net.rcode.nanomaps.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.jboss.netty.util.CharsetUtil;

/**
 * Feed various content to this in order to derive a hash value
 * suitable for representing the version of the content.
 * 
 * @author stella
 *
 */
public class IdentityHasher {
	private static final String SEED="1:";
	private MessageDigest digest;
	
	public IdentityHasher() {
		try {
			digest=MessageDigest.getInstance("MD5");
			digest.update(SEED.getBytes(CharsetUtil.UTF_16BE));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Cannot get MD5 digester", e);
		}
	}
	
	public void append(byte[] data) {
		digest.update(data);
	}
	
	public void append(String data) {
		digest.update(data.getBytes(CharsetUtil.UTF_16BE));
	}
	
	public void appendSep() {
		digest.update(new byte[] { ':' });
	}
	
	public String getHash() {
		byte[] hash=digest.digest();
		return Base64.encodeBase64URLSafeString(hash);
	}
}
