package com.grub.svg4mobile;

import org.w3c.dom.*;

/**
 * Interfaz estrategia para parsear elementos.
 */
public interface ParseStrategy
{
	/**
	 * Parsea un elemento.
	 * @param nodo Nodo correspondiente a un elemento.
	 * @return Devuelve una Figure. 
	 */
     public Figure parse_element(Element nodo);
}