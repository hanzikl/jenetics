/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.jenetics.util.IO;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version @__version__@ &mdash; <em>$Date: 2014-01-29 $</em>
 * @since @__version__@
 */
public class XMLMarshallingTest {

	@Test(dataProvider = "objects")
	public void jaxbJavolutionCompatibility(final PersistentObjects<?> object)
		throws IOException
	{
		test(object.getValue(), IO.jaxb, IO.xml);
	}

	@Test(dataProvider = "objects")
	public void javolutionJAXBCompatibility(final PersistentObjects<?> object)
		throws IOException
	{
		test(object.getValue(), IO.xml, IO.jaxb);
	}

	private static void test(final Object object, final IO write, final IO read)
		throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		write.write(object, out);

		final byte[] data = out.toByteArray();
		System.out.println(new String(data));
		final ByteArrayInputStream in = new ByteArrayInputStream(data);
		final Object copy = read.read(in);

		Assert.assertEquals(copy, object);
	}

	@DataProvider(name = "objects")
	public Object[][] getObjects() {
		final Object[][] objects = new Object[PersistentObjects.VALUES.size()][1];
		for (int i = 0; i < objects.length; ++i) {
			objects[i] = new Object[]{PersistentObjects.VALUES.get(i)};
		}

		return objects;
	}
}
