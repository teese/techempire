# techempire
This is a simple strategy game. It's a project that I use to learn Java.

You play the role as the leader of a scientific grant agency. Your aim is to create a technology empire by funding the right scientists.

In each round, you have four grant applications to choose from. Since the projects themselves are very technical, the only way to identify successful projects is to examine the publicly available information on the authors:
 
* the h-index of the researcher
* the age of the researcher

The four choices are currently displayed as follows.

![choose researcher](https://raw.githubusercontent.com/teese/techempire/master/docs/images/choose_researcher.png)

Good researchers have a high h-index for their age. Here's a plot of age vs h-index for a number of researchers available on google scholar.

![choose researcher](https://raw.githubusercontent.com/teese/techempire/master/docs/images/hindex_vs_age.png)

Later on in the game, it will be possible to get more detailed and useful information, such as the output of their previously funded projects. Finding good researchers and projects is really not that easy.

The researcher names and h-indices refer to REAL scientists, who have publicly available information on google scholar, orchid, or researcherID. You can replace this with other researchers that you might know. 

Your initial aim is to gather advances in the fields of Biology, Physics or Chemistry. When you have gathered enough basic research points in one of these fields, you can start funding applied research projects, which hopefully spin off into a new technology company.

Currently only the initial part of the game is initiated.

![choose researcher](https://raw.githubusercontent.com/teese/techempire/master/docs/images/gameover.png)


To play. Run the jarfile in the out/artefacts/techempire_jar folder. As arguments, include the number of points required to win, and the csv file containing the researchers.
e.g.

    java -jar techempire.jar -p 1000 -r D:\github_projects\techempire\docs\researchers.csv