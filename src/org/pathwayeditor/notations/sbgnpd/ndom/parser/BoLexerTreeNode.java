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

import java.util.LinkedList;
import java.util.Queue;

public class BoLexerTreeNode implements ILexerTreeNode {
	private final IToken token;
	private final Queue<IToken> children;
	private IToken currentChild = null;
	
	public BoLexerTreeNode(IToken currToken){
		this.token = currToken;
		this.children = new LinkedList<IToken>();
	}
	
	void addChild(IToken child){
		this.children.add(child);
	}
	
	
	@Override
	public IToken getParentToken() {
		return this.token;
	}

	@Override
	public void nextChild() {
		this.currentChild = this.children.poll();
	}
	
	@Override
	public int numChildren(){
		return this.children.size();
	}

	@Override
	public IToken getCurrentChild() {
		return this.currentChild;
	}

	@Override
	public IToken peekNextChild() {
		return this.children.peek();
	}

	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(parent=");
		buf.append(this.token);
		buf.append(",currChild=");
		buf.append(this.currentChild);
		buf.append(",children=");
		buf.append(this.children);
		buf.append(")");
		return buf.toString();
	}
}
