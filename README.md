Welcome Mercedes SRE

n*1 Argument:
>(1) Fetch (2) Live (3) History (4) Backup")  
> Command Required  
> Pick the number of the command to fire.  
> This represents the main Feature of the program


*2 Argument:
>(y) Yes, Enable Optional Switch  
>(n) No, Disable Optional Switch  
> Command Required  
> Choose to enable or disable the optional Switch that can have different meanings depending on the command used  
> Main purpose is to display info about the data that is processed at that moment

*3 Argument:
> (abcdef) Type a word to only process the urls that match this same word  
> Command Not Required


*Command anatomy
>_[Command_Name] [Optional Arg] [Subset Arg]_

*Examples*:
> 1 y google  
> 2 n


*Notes on specific Commands*

- FETCH


- LIVE

You can configure the pooling interval for Live command on application.properties under:
>live.pooling.interval (default value is 5 seconds)

- HISTORY
    display the url used and respective data fetched.
    The Data includes group of records with HTTP Status Code and the respective moment it was captured


