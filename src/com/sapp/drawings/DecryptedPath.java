package com.sapp.drawings;

/** The object contains file data decrypted to id, revision and title
 *
 */

public class DecryptedPath {

    private final String id, revision, title;

    public DecryptedPath(String id, String revision, String title){
        this.id = id;
        this.revision = revision;
        this.title = title;
    }

    public String getId() { return id; }
    public String getRevision() { return revision; }
    public String getTitle() { return title; }
}
