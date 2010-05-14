package com.grub.svg4mobile;

import java.util.*;
import org.w3c.dom.*;

public class ParseContext
{
  private ParseStrategy ps;
  private Element el;
	
        public ParseContext(ParseStrategy e, Element el)
	{
		this.ps = e;
		this.el = el;
	}
	
	public void setStrategy(ParseStrategy e)
	{
          this.ps = e;
	}
	
	public Figure runStrategy()
	{	
                return ps.parse_element(el);
	}

}