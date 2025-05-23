package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import seedu.duke.exception.MTException;
import seedu.duke.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Contains unit tests for the {@link Storage} class.
 * Verifies file operations including loading, saving, and error handling.
 *
 * @author rchlai
 */
class StorageTest {
    private Storage storage;
    private Path originalFilePath;

    //@@author rchlai
    /**
     * Sets up the test environment before each test method execution.
     * Backs up the original data file if it exists and
     * initializes a new {@link Storage} instance.
     *
     * @param tempDir The temporary directory provided by JUnit for test isolation
     * @throws IOException If an I/O error occurs during file operations
     *
     */
    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        // Backup original file if exists
        originalFilePath = Path.of("mt.txt");

        if (Files.exists(originalFilePath)) {
            Files.move(originalFilePath,
                    tempDir.resolve("mt_backup.txt"));
        }

        storage = new Storage(); // Using actual no-arg constructor
    }

    /**
     * Tests that loading entries from a non-existent file returns
     * an empty list.
     * Verifies the Storage class handles missing files gracefully.
     *
     */
    @Test
    void loadEntries_noFileExists_returnsEmptyList() {
        // Ensure file doesn't exist
        assertFalse(Files.exists(Path.of("mt.txt")));

        ArrayList<String> result = assertDoesNotThrow(
                () -> storage.loadEntries());

        assertTrue(result.isEmpty(),
                "Should return empty list when file doesn't exist");
    }

    /**
     * Tests that saving entries to an invalid file path
     * throws an {@link MTException}.
     * Simulates an invalid path by creating a directory with
     * the target filename.
     * Verifies:
     * 1. Exception is thrown
     * 2. Exception message contains expected error information
     *
     * @throws IOException If directory creation fails
     */
    @Test
    void saveEntries_invalidFile_throwsException() throws IOException {
        // Create a directory with the same name as the storage file
        Files.createDirectory(Path.of("mt.txt"));

        ArrayList<String> testData = new ArrayList<>();
        testData.add("[Expense] Test Value = $10.00 |Test| (2023-10-17)");

        // Verify exception
        MTException thrown = assertThrows(MTException.class,
                () -> storage.saveExpenses(testData));

        assertTrue(thrown.getMessage().contains("Error saving entries"));
    }

    /**
     * Cleans up the test environment after each test method execution.
     * Restores the original data file if it existed or deletes
     * test files.
     *
     * @param tempDir The temporary directory provided by JUnit
     * @throws IOException If an I/O error occurs during cleanup
     */
    @AfterEach
    void tearDown(@TempDir Path tempDir) throws IOException {
        // Restore original file if it existed
        if (Files.exists(tempDir.resolve("mt_backup.txt"))) {
            Files.move(tempDir.resolve("mt_backup.txt"),
                    originalFilePath);
        } else {
            // Clean up test file
            Files.deleteIfExists(Path.of("mt.txt"));
        }
    }
}
