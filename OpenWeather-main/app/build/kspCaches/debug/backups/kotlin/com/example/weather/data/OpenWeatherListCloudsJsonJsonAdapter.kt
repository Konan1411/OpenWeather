// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.example.weather.`data`

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.text.buildString

public class OpenWeatherListCloudsJsonJsonAdapter(
  moshi: Moshi,
) : JsonAdapter<OpenWeatherListCloudsJson>() {
  private val options: JsonReader.Options = JsonReader.Options.of("all")

  private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java, emptySet(), "all")

  public override fun toString(): String = buildString(47) {
      append("GeneratedJsonAdapter(").append("OpenWeatherListCloudsJson").append(')') }

  public override fun fromJson(reader: JsonReader): OpenWeatherListCloudsJson {
    var all: Int? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> all = intAdapter.fromJson(reader) ?: throw Util.unexpectedNull("all", "all", reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return OpenWeatherListCloudsJson(
        all = all ?: throw Util.missingProperty("all", "all", reader)
    )
  }

  public override fun toJson(writer: JsonWriter, value_: OpenWeatherListCloudsJson?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("all")
    intAdapter.toJson(writer, value_.all)
    writer.endObject()
  }
}
