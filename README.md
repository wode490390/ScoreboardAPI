Example plugin usage
-------------

```java
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        FakeScoreboard scoreboard = new FakeScoreboard();
        Objective obj = new Objective("test", new ObjectiveCriteria("dummy", true));

        DisplayObjective dobj = new DisplayObjective();
        dobj.objective = obj;
        dobj.sortOrder = ObjectiveSortOrder.DESCENDING;
        dobj.displaySlot = ObjectiveDisplaySlot.SIDEBAR;

        obj.setDisplayName(TextFormat.YELLOW + "Game" + TextFormat.WHITE + "Team");
        obj.setScore(1, "  " + TextFormat.RED + "---------------  ", 6);
        obj.setScore(2, "cau", 5);
        obj.setScore(3, "cau 1", 4);
        obj.setScore(4, "cau 2", 3);
        obj.setScore(5, "cau 3", 2);
        obj.setScore(6, "cau 4", 1);

        scoreboard.objective = dobj;

        this.getServer().getScheduler().scheduleDelayedTask(this, new NukkitRunnable() {
            @Override
            public void run() {
                scoreboard.addPlayer(p);
            }
        }, 60);

        this.getServer().getScheduler().scheduleDelayedTask(this, new NukkitRunnable() {
            @Override
            public void run() {
                obj.setScore(5, new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis())), 12);
                scoreboard.update();
            }
        }, 100);
    }
```