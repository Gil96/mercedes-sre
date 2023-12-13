# Mercedes SRE - Website Scanner

### Welcome
 
This is a Website scanner project that is able to retrieve HTTP status code information about a list of websites/services.


### Interaction

**Command anatomy**
>_[Command_Name] [Optional Arg] [Subset Arg]_

1st Argument: _Command Name_
>(1) Fetch  
>(2) Live   
>(3) History  
>(4) Backup  
>(5) Restore   
> Pick the number of the command you desire to run.  
> Each command represent a different feature of the project and can be complemented with extra arguments, more info below...


2nd Argument: _Optional Argument_
>(y) Yes, Enable Optional Switch  
>(n) No, Disable Optional Switch  
> Argument Required   
> Main purpose is to display info about the data that is processed at that moment
> There are commands such as Backup which does not care about the value of this argument

3rd Argument: _Subset Argument_
> (abcdef) Type a word to only process the urls that match this same word  
> Argument Not Required

*Examples*:

`````1 n ````` Fetch command with output disabled and fetch all websites from the list  
`````1 y ````` Fetch command with output enabled and fetch all websites from the list  
`````1 y googl````` Fetch command with output enabled to any "google" websites     
```2 n``` Live command with output disabled   
```2 y``` Live command printing information gathered in each iteration from all websites   
```2 y quora``` Live command printing information gathered in each iteration only for "quora" services  
```3 y``` History command - printing the whole datastore  
```4 y``` Backup  command  
```5 y``` Restore command

### Notes on specific Commands

- FETCH


- LIVE

  - You can configure the pooling interval for Live command on application.properties under:
      >live.pooling.interval (default value is 5 seconds)

  - Also can configure the time duration of live feature on:
  - >live.time.duration (default value is 20 seconds)
- HISTORY
  - display the url used and respective data fetched.
     The Data includes group of records with HTTP Status Code and the respective moment it was captured.


- BACKUP
  - Backup command will write data on backup.json file on the root project.
  The path file can be changed on application.properties


- RESTORE
  - There is a restore.json file with some valid data for manual testing using Restore command
  
- MISC 
  - The list of websites are stored in resources/static/websites.txt and will be placed in the app memory in run time
  - The application has a connection timeout set, which means it does not wait no longer than 2 seconds per connection - (this value can be set on application.properties)
  - > rest.connection.timeout = 2