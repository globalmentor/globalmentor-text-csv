package com.garretwilson.csv;

import java.io.*;

import static com.garretwilson.csv.CSVConstants.*;
import static com.globalmentor.java.CharSequences.*;
import static com.globalmentor.java.Characters.*;
import static com.globalmentor.java.StringBuilders.*;

/**Serializes Comma Separated Value (CSV) information
@author Garret Wilson
*/
public class CSVSerializer
{
	/**Serializes the given objects to the provided writer.
	@param writer The writer to which the objects will be serialized.
	@param objects The objects to serialize, each of which may be <code>null</code>.
	@exception IOException if there is an error writing the CSV record.
	*/
	public static void serialize(final Writer writer, final Object... objects) throws IOException
	{
		for(int i=0; i<objects.length; ++i)	//look at each object
		{
			if(i>0)	//if this isn't the first object
			{
				writer.write(FIELD_DELIMITER_CHAR);	//delimit the fields
			}
			final Object object=objects[i];	//get a reference to this object
			if(object!=null)	//if there is an object to write
			{
				final String field=encodeField(object.toString());	//get the string value of the object
				writer.write(field);	//write the field
			}
		}
		writer.write(RECORD_DELIMITER_STRING);	//write the end of the record
	}

	/**Encodes a string to be represented in a CSV field.
	If the character sequence includes a comma, double-quote, or a line break (CR or LF),
	the string is encoded and placed in quotes. Double-quotes are escaped by doubling them.
	@param charSequence The character sequence to encode. 
	@return An encoded string appropriate for serialization as a CSV field.
	 */
	public static String encodeField(final CharSequence charSequence)
	{
		if(charIndexOf(charSequence, RESTRICTED_CHARACTERS)>=0)	//if a restricted character is in the character sequence
		{
			final StringBuilder stringBuilder=new StringBuilder(charSequence);	//create a new string buffer to process the string 
			replace(stringBuilder, QUOTATION_MARK_CHAR, ESCAPED_QUOTATION_MARK_STRING);	//replace all quotes with doubled quotes
			stringBuilder.insert(0, QUOTATION_MARK_CHAR);	//prepend a quotation mark
			stringBuilder.append(QUOTATION_MARK_CHAR);	//append a quotation mark
			return stringBuilder.toString();	//return the string we created
		}
		else	//if there is no restricted character
		{
			return charSequence.toString();	//return the character sequence we started with
		}
	}
}
