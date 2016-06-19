package com.test.es;

import com.lzx.elasticsearch.jest.JestUtil;

/**
 * @author lizx
 * @data 2016-6-5下午12:22:18
 */
public class JestTest {

	public static void main(String[] args) {
		String index = "lzxes";
		String query = "";
		String searchIndex = JestUtil.searchIndex(index, "", query);
		System.out.println(searchIndex);
	}
}
