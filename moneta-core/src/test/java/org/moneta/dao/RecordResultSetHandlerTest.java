/*
 * This software is licensed under the Apache License, Version 2.0
 * (the "License") agreement; you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moneta.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moneta.HSqlTestBase;
import org.moneta.types.Record;

public class RecordResultSetHandlerTest extends HSqlTestBase {
	
	RecordResultSetHandler handler;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		handler = new RecordResultSetHandler();
	}
	
	@Test
	public void testBasicHappyPath() throws Exception {
		QueryRunner runner = new QueryRunner();
		Record[] recArray = runner.query(nativeConnection, 
				"select * from INFORMATION_SCHEMA.SYSTEM_TABLES", handler);
		Assert.assertTrue(recArray != null);
		Assert.assertTrue(recArray.length == 92);
		
		handler.setStartRow(90L);
		recArray = runner.query(nativeConnection, 
				"select * from INFORMATION_SCHEMA.SYSTEM_TABLES", handler);
		Assert.assertTrue(recArray != null);
		Assert.assertTrue(recArray.length == 3);
		
		handler.setStartRow(null);
		handler.setMaxRows(10L);
		recArray = runner.query(nativeConnection, 
				"select * from INFORMATION_SCHEMA.SYSTEM_TABLES", handler);
		Assert.assertTrue(recArray != null);
		Assert.assertTrue(recArray.length == 10);
	}

}
