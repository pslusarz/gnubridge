<html>
<body>
<h1>GNUBridge</h1>

<?php
$highest_subversion = 0;
$date_modified = "";
if ($handle = opendir('.')) {
    
    while (false !== ($file = readdir($handle))) {
      if ($file != "." && $file != "..") {
              $fileChunks = array_reverse(explode(".", $file));
              $ext = $fileChunks[0];
              if ($ext == "jar") {
                $current_subversion = $fileChunks[1];
                if ($current_subversion > $highest_subversion) {
                  $highest_subversion = $current_subversion;
                  $date_modified = date ("F d Y H:i:s.", filemtime($file));
                }
              }
      }
    }
    
    closedir($handle);
    
}
$version = "0.1." . $highest_subversion;
echo "Latest version: $version published on $date_modified<br>";
?>


<p>GNUBridge is a Contract Bridge program written in Java and available under 
<a href="http://www.gnu.org/licenses/gpl-3.0.html">GNU public license v. 3</a>. 
<hr>
<table border="0" cellspacing="10">
<tr>
  <td> <b>Users</b></td>
  <td> <b>Developers</b></td>
</tr>
<tr>
  <td>
    <?php print "You can download the jar <a href=\"gnubridge-$version.jar\">gnubridge-$version.jar</a>, and run it with <i>% 
java -jar gnubridge-$version.jar</i>, or by double-clicking if you're in Windows (but beware of Internet Explorer - it will try to force a .zip extension on the file). You will need JRE 1.6 or later." ?>
  </td>
  <td>
    <a href="http://gnubridge.blogspot.com/">Developer blog</a>
  </td>
</tr>
<tr>
  <td>
   Screenshots: <a href="./early-bidding.png">bidding</a> and <a href="gnubridge-earlygameplay.png">gameplay</a>.
   </td>
   <td>
     There is a <a href="http://parabuild.viewtier.com:8080/parabuild/index.htm?cid=bb8e&view=detailed&buildid=13933&displaygroupid=-1">Continuous Integration feed</a>. Continuous Integration for Gnubridge is provided by <a
href="http://www.viewtier.com/products/parabuild/index.htm">Parabuild</a>.
   </td>
<tr>
<tr>
  <td>
  There is a <a href="http://lists.gnubridge.org/listinfo.cgi/users-gnubridge.org">mailing list</a> you can join where
you can post your questions or watch for program updates. You can also 
<a href="http://lists.gnubridge.org/pipermail/users-gnubridge.org/">browse the archive</a>.
  </td>
  <td>Source code is available by connecting anonymously to the subversion repository 
at http://gnubridge.org/subversion/gnubridge or by <a href="./subversion/gnubridge/">browsing online</a>. 
  </td>
</tr>
</table>
<hr>
<p><b>November 25, 2009</b><br>
Version 0.1.14. Two new features: player can review most recent trick (click on the card icon in upper left corner of the table), and there's visual feedback on the time remaining for computer to make a move (countdown timer). Time per move was reduced to 15 seconds. A questionable pruning strategy was disabled (play lowest card to lost trick).
</p>
<p><b>September 29, 2009</b><br>
Version 0.1.12. Fixed bugs in search. These bugs were introduced as I was putting in new optimization strategies. I put in a set of tests 
to assure these kinds of problems are automatically caught in the future.<br>
I also peeled away the Swing GUI classes from tests, so that the CI server build is finally working. This is important, because you can now download the latest version
that's automatically tested and build on the CI server each time a code change is made.
</p>
<p><b>July 20, 2009</b><br>
Tonight I worked a bit on GNUBridge's continuous integration (CI). I wanted a model where every change to repository would result in all tests being run, and a publication of the latest jar. Folks at Viewtier have been great at giving me all I asked for so far, and so tonight we have the first take. I could not get the acceptance tests to run on the server yet, and so it's just the unit tests for now. What does CI mean for end-users? You can monitor the code change comments, and if you want to try out the new features, you can just get the latest jar from the CI server, without waiting for a formal release.
</p>
<p><b>July 16, 2009</b><br>
Folks at Viewtier have genrously set up a <a href="http://parabuild.viewtier.com:8080/parabuild/index.htm?cid=bb8e&view=detailed&buildid=13933&displaygroupid=-1">Continuous Integration project</a> for GNUBridge. For now it just builds the jar, but I'm planning to get it to run the acceptance tests shortly.  
</p>
<p><b>July 14, 2009</b><br>
Version 0.1.11. Fixed a bug with pruning cards that were in sequence with cards already played. Removed this pruning strategy for now until I can get it fixed.
</p>
<p><b>July 12, 2009</b><br>
Version 0.1.10. Fixed a subtle bug with always playing lowest card if trick cannot be taken. Basic rebids: raise partner, show unbid suit, rebid original (from Pavlicek's Lesson 5, except NT rebids). Responding bid rules only fire if it's a true response, not a rebid.
</p>
<p><b>June 22, 2009</b><br>
Version 0.1.9. Responses to 1 Color in Pavlicek's lesson 4.
</p>
<p><b>June 16, 2009</b><br>
Version 0.1.8. All things being equal, play lowest value card. This should result in a visibly stronger play. Fixed bug with trick display count lagging one trick behind. Display contract, instead of just the trump, and display tricks taken by each pair.
</p>
<p><b>June 9, 2009</b><br>
Version 0.1.7. If there's only one reasonable move, take it, instead of waiting for the search to terminate. This should speed up gameplay considerably.
</p>
<p><b>June 8, 2009</b><br>
Version 0.1.6. Fixed a crash when dealing with partner's pass in response to 1NT. Raise major suit opening, and more accurate distributional point counts (basically made it half-way through Pavlicek's lesson 4 before discovering the crash bug). I don't take crashes lightly, so I added a suite of random end-to-end tests that will hopefully catch bugs like that before they make it into a release.
</p>
<p><b>May 26, 2009</b><br>
Version 0.1.5. Deeper search through duplicate position removal and a simple heuristic. On my machine I'm able to get 13 cards to go 4 tricks deep, and then up all the way to full depth at 7. Search is capped at 45 seconds. Also, at some point in the past, I fixed the bug where dummy was showing cards before West played the first card.<br>
I am now thinking of going back to Pavlicek's lessons and work on improving the bidding in the next few releases.
</p>
<p><b>May 21, 2009</b><br>
Some time this weekend I'm planning to post a new version of GNUBridge with improved search algorithm. I just noticed, it's been a year 
since the last update. This project is not dead, just idling in a self-perpetuating "lack of feedback" loop. Without hearing from
actual users, I am prone to spend time on search optimization and refactoring code. Without new friendly features few people want to 
try GNUBridge out, which means no feedback, which means even more invisible search optimizations... I'll try to break out of that loop 
a bit this summer and focus somewhat on usability.</p>
<p><b>May 4, 2008</b><br>
Version 0.1.4. Prune moves of sequential cards on player's hand. Based on my experiments this provided an order of magnitude 
improvement allowing the program to search one trick deeper. Introduced variable depth search based on how many 
tricks have been played. In the beginning, the program searches 3 tricks deep, when there are 7 cards left the 
search goes up to 4 tricks, and at 5 the full tree is searched.
<p><b>April 14, 2008</b><br>
Version 0.1.3. Fixed bug with trick count and dummy cards not being updated in time. Human hand also gets updated after a card 
is played. Can play a card by double-clicking. 
<p><b>April 10, 2008</b><br>
Better prompting to play a card, and high card is highlighted when displaying previous trick. 
<p><b>April 6, 2008</b><br>
Squeezed additional 50 pixels out of the vertical height on the play screen.  
<p><b>April 4, 2008</b><br>
Made and build script to create an executable jar and made the jar available for download.
<p><b>April 1, 2008</b><br> 
I never thought it'd happen, but here it is - first cut of a playable bridge game. Let's see if I can 
make the committ before April Fools ends in 3 minutes... I will shortly make a distributable jar. 
<p><b>Feb. 18, 2008</b><br> 
The first cut of the program is to consist of 3 modules: double-dummy solver, bidding, and GUI. Double-dummy 
solver, whose role is to come up with optimal play knowing all the cards on the table, is currently able to 
search 5-6 tricks deep in a few minutes. It uses plain alpha-beta pruning. I am currently struggling with the 
bidding module. I'm working through Richard Pavlicek's Beginner Bridge online lessons and implementing all the
bidding rules as I see them. It is very tedious work, even as I'm constantly reshaping the classes to make
 putting in new rules easier. I am on lesson 4 out of 12, and don't see getting done with it in a month.   
<p><b>Feb. 6, 2008</b><br> 
The program is not playable yet, but you can download the latest source 
code by connecting anonymously to the subversion repository 
at http://gnubridge.org/subversion/gnubridge or by <a href="./subversion/gnubridge/">browsing online</a> 
</body>
</html>