package io.file;

import model.Library;
import model.StaffRepository;

public interface FileManager {
    StaffRepository importStaffRepoData();
    Library importLibraryData();
    void exportData(StaffRepository staffRepository, Library library);

}
