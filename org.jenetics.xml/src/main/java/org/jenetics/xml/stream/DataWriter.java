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
package org.jenetics.xml.stream;

import static java.util.Objects.requireNonNull;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Functional interface for writing a given data object to a given XML
 * writer. The implementations have to handle {@code null} data elements
 * accordingly.
 *
 * @param <T> the data type
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
@FunctionalInterface
public interface DataWriter<T> {

	public void write(final T data, final XMLStreamWriter writer)
		throws XMLStreamException;

	public default <P> DataWriter<T> elem(final String name, final Function<T, P> property) {
		return (data, writer) -> {
			writer.writeStartElement(name);
			write(null, null);
			writer.writeEndElement();
		};
	}

	public static <T> DataWriter<T> of(final String name) {
		return (data, writer) -> {
			writer.writeStartElement(name);
			writer.writeEndElement();
		};
	}

	public static <T> DataWriter<T> elem(final String name) {
		requireNonNull(name);

		return (data, writer) -> {
			if (data != null) {
				writer.writeStartElement(name);
				writer.writeCharacters(data.toString());
				writer.writeEndElement();
			}
		};
	}

	public static <A, B> DataWriter<A> elem(
		final String name,
		final DataWriter<B> child,
		final Function<A, B> accessor
	) {


		return null;
	}

}

