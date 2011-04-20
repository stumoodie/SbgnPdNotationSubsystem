/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
package org.pathwayeditor.notations.sbgnpd.ndom.parser;

public class TreeParseException extends Exception {
	private static final long serialVersionUID = 6836465476036389491L;

	public TreeParseException(IToken current, String msg) {
		super(createMsg(current, msg));
	}

	
	public TreeParseException(IToken current, String msg, Throwable t) {
		super(createMsg(current, msg), t);
	}

	
	private static String createMsg(IToken current, String msg){
		StringBuilder buf = new StringBuilder("Parse error at token=");
		buf.append(current.toString());
		buf.append(". Error msg=");
		buf.append(msg);
		return buf.toString();
	}
}
