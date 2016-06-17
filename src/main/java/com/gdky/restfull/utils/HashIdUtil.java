package com.gdky.restfull.utils;

import gov.gdgs.zs.configuration.Config;

import org.hashids.Hashids;

import com.gdky.restfull.exception.ResourceNotFoundException;

public class HashIdUtil {

	public static String encode(Long id){
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		return hashids.encode(id);
	}
	
	public static String encode(Integer id){
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		return hashids.encode(id.longValue());
	}
	
	public static Long decode(String hash){
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		long[] id = hashids.decode(hash);
		if(id.length<1){
			throw new ResourceNotFoundException(hash);
		}
		return id[0];
	}
}
