package me.timelytask.util

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import java.nio.file.{Files, Path}

class FileIOSpec extends AnyFunSuite with Matchers {

  // Helper methods
  private def withTempFile(testCode: Path => Any): Unit = {
    val tempFile = Files.createTempFile("test", ".txt")
    try {
      testCode(tempFile)
    } finally {
      Files.deleteIfExists(tempFile)
    }
  }

  private def withTempDirectory(testCode: Path => Any): Unit = {
    val tempDir = Files.createTempDirectory("test_dir")
    try {
      testCode(tempDir)
    } finally {
      Files.deleteIfExists(tempDir)
    }
  }


  test("writeToFile should write content and return true") {
    withTempFile { tempFile =>
      // Setup
      val content = "Hello, World!"

      // Action
      val success = FileIO.writeToFile(tempFile.toString, content)

      // Assert
      success should be (true)
      val readContent = new String(Files.readAllBytes(tempFile))
      readContent should equal (content)
    }
  }

  test("readFromFile should read content correctly") {
    withTempFile { tempFile =>
      // Setup
      val expectedContent = "This is a test file."
      Files.write(tempFile, expectedContent.getBytes)

      // Action
      val readResult = FileIO.readFromFile(tempFile.toString)

      // Assert
      readResult should be (Some(expectedContent))
    }
  }

  test("readFromFile should return None for a non-existent file") {
    // Setup
    val nonExistentPath = "this/path/does/not/exist.txt"

    // Action
    val result = FileIO.readFromFile(nonExistentPath)

    // Assert
    result should be (None)
  }

  test("writeToFile should return false for an invalid path (e.g., a directory)") {
    withTempDirectory { tempDir =>
      // Setup
      val contentToWrite = "some content"

      // Action
      val success = FileIO.writeToFile(tempDir.toString, contentToWrite)

      // Assert
      success should be (false)
    }
  }
}