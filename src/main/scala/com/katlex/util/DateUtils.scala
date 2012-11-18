/*
 * Copyright (c) 2012 Alexey Lunacharsky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.katlex.util

import java.util.Date

/**
 * Helper class to convenient work with dates using implicit conversions
 */
object DateUtils {

  /**
   * Different time intervals in milliseconds
   */
  object Milliseconds {
    val MILLISECOND = 1L
    val SECOND = 1000 * MILLISECOND
    val MINUTE = 60 * SECOND
    val HOUR = 60 * MINUTE
    val DAY = 24 * HOUR
    val WEEK = 7 * DAY
  }

  /**
   * Date optional additional methods
   * @param date this date
   */
  class DateOps(date:Date) {
    /**
     * Adds specified amount of time to this date
     * @param time time in milliseconds
     * @return new date shifted by given amount of time
     */
    def + (time:Long) = new Date(date.getTime + time)

    /**
     * Subtracts specified amount of time from this date
     * @param time time in milliseconds
     * @return new date shifted by given amount of time
     */
    def - (time:Long) = this + (- time)

    /**
     * Calculates distance in milliseconds between this date and other date
     * @param other other date to calc the distance to
     * @return amount of milliseconds between dates
     */
    def - (other:Date) = date.getTime - other.getTime
  }
  implicit def dateOps(d:Date):DateOps = new DateOps(d)

  /**
   * Long additional methods
   * @param l this long
   */
  class LongOps(l:Long) {
    /**
     * @return Date shifted from now by amount of this long
     */
    def fromNow = now + l
  }
  implicit def longOps(l:Long):LongOps = new LongOps(l)

  /**
   * @return Current date
   */
  def now = new Date
}