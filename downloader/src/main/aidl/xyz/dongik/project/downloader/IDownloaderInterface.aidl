// IDownloaderInterface.aidl
package xyz.dongik.project.downloader;

// Declare any non-default types here with import statements

interface IDownloaderInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     void enqueueUrl(String url);
     void download();
}
